package com.example.cuiduo.wifitesting.codec;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.BufferDataException;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * A {@link ProtocolDecoder} which decodes a text line into a string.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class TextDecoder implements ProtocolDecoder {
	private final AttributeKey CONTEXT = new AttributeKey(getClass(), "context");
	private static final Logger logger = Logger.getLogger(TextDecoder.class);

	private final Charset charset;

	/** The delimiter used to determinate when a line has been fully decoded */
	private final LineDelimiter delimiter;

	/** An IoBuffer containing the delimiter */
	private IoBuffer delimBuf;

	/** The default maximum Line length. Default to 1024. */
	private int maxLineLength = 1024;

	/** The default maximum buffer length. Default to 128 chars. */
	private int bufferLength = 128;

	/**
	 * Creates a new instance with the current default {@link Charset} and
	 * {@link LineDelimiter#AUTO} delimiter.
	 */
	public TextDecoder() {
		this(LineDelimiter.AUTO);
	}

	/**
	 * Creates a new instance with the current default {@link Charset} and the
	 * specified <tt>delimiter</tt>.
	 */
	public TextDecoder(String delimiter) {
		this(new LineDelimiter(delimiter));
	}

	/**
	 * Creates a new instance with the current default {@link Charset} and the
	 * specified <tt>delimiter</tt>.
	 */
	public TextDecoder(LineDelimiter delimiter) {
		this(Charset.defaultCharset(), delimiter);
	}

	/**
	 * Creates a new instance with the spcified <tt>charset</tt> and
	 * {@link LineDelimiter#AUTO} delimiter.
	 */
	public TextDecoder(Charset charset) {
		this(charset, LineDelimiter.AUTO);
	}

	/**
	 * Creates a new instance with the spcified <tt>charset</tt> and the
	 * specified <tt>delimiter</tt>.
	 */
	public TextDecoder(Charset charset, String delimiter) {
		this(charset, new LineDelimiter(delimiter));
	}

	/**
	 * Creates a new instance with the specified <tt>charset</tt> and the
	 * specified <tt>delimiter</tt>.
	 */
	public TextDecoder(Charset charset, LineDelimiter delimiter) {
		if (charset == null) {
			throw new IllegalArgumentException("charset parameter shuld not be null");
		}

		if (delimiter == null) {
			throw new IllegalArgumentException("delimiter parameter should not be null");
		}

		this.charset = charset;
		this.delimiter = delimiter;

		// Convert delimiter to ByteBuffer if not done yet.
		if (delimBuf == null) {
			IoBuffer tmp = IoBuffer.allocate(2).setAutoExpand(true);

			try {
				tmp.putString(delimiter.getValue(), charset.newEncoder());
			} catch (CharacterCodingException cce) {
				logger.error(cce);
			}

			tmp.flip();
			delimBuf = tmp;
		}
	}

	/**
	 * Returns the allowed maximum size of the line to be decoded. If the size
	 * of the line to be decoded exceeds this value, the decoder will throw a
	 * {@link BufferDataException}. The default value is <tt>1024</tt> (1KB).
	 */
	public int getMaxLineLength() {
		return maxLineLength;
	}

	/**
	 * Sets the allowed maximum size of the line to be decoded. If the size of
	 * the line to be decoded exceeds this value, the decoder will throw a
	 * {@link BufferDataException}. The default value is <tt>1024</tt> (1KB).
	 */
	public void setMaxLineLength(int maxLineLength) {
		if (maxLineLength <= 0) {
			throw new IllegalArgumentException("maxLineLength (" + maxLineLength + ") should be a positive value");
		}

		this.maxLineLength = maxLineLength;
	}

	/**
	 * Sets the default buffer size. This buffer is used in the Context to store
	 * the decoded line.
	 *
	 * @param bufferLength
	 *            The default bufer size
	 */
	public void setBufferLength(int bufferLength) {
		if (bufferLength <= 0) {
			throw new IllegalArgumentException("bufferLength (" + maxLineLength + ") should be a positive value");

		}

		this.bufferLength = bufferLength;
	}

	/**
	 * Returns the allowed buffer size used to store the decoded line in the
	 * Context instance.
	 */
	public int getBufferLength() {
		return bufferLength;
	}

	/**
	 * {@inheritDoc}
	 */
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {

		Context ctx = getContext(session);

		try {
			byte[] data = new byte[in.limit()];
			in.get(data);
			CharsetDecoder decoder = ctx.getDecoder();

			CharBuffer buffer = decoder.decode(ByteBuffer.wrap(data));
			String str = buffer.toString();
			writeText(session, str, out);
		} finally {
			// buf.clear();
		}

	}

	/**
	 * Return the context for this session
	 */
	private Context getContext(IoSession session) {
		Context ctx;
		ctx = (Context) session.getAttribute(CONTEXT);

		if (ctx == null) {
			ctx = new Context(bufferLength);
			session.setAttribute(CONTEXT, ctx);
		}

		return ctx;
	}

	/**
	 * {@inheritDoc}
	 */
	public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 */
	public void dispose(IoSession session) throws Exception {
		Context ctx = (Context) session.getAttribute(CONTEXT);

		if (ctx != null) {
			session.removeAttribute(CONTEXT);
		}
	}

	/**
	 * By default, this method propagates the decoded line of text to
	 * {@code ProtocolDecoderOutput#write(Object)}. You may override this method
	 * to modify the default behavior.
	 *
	 * @param session
	 *            the {@code IoSession} the received data.
	 * @param text
	 *            the decoded text
	 * @param out
	 *            the upstream {@code ProtocolDecoderOutput}.
	 */
	protected void writeText(IoSession session, String text, ProtocolDecoderOutput out) {
		out.write(text);
	}

	/**
	 * A Context used during the decoding of a lin. It stores the decoder, the
	 * temporary buffer containing the decoded line, and other status flags.
	 *
	 * @author <a href="mailto:dev@directory.apache.org">Apache Directory
	 *         Project</a>
	 * @version $Rev$, $Date$
	 */
	private class Context {
		/** The decoder */
		private final CharsetDecoder decoder;

		/** The temporary buffer containing the decoded line */
		private final IoBuffer buf;

		/** The number of lines found so far */
		private int matchCount = 0;

		/** A counter to signal that the line is too long */
		private int overflowPosition = 0;

		/** Create a new Context object with a default buffer */
		private Context(int bufferLength) {
			decoder = charset.newDecoder();
			buf = IoBuffer.allocate(bufferLength).setAutoExpand(true);
		}

		public CharsetDecoder getDecoder() {
			return decoder;
		}

		public IoBuffer getBuffer() {
			return buf;
		}

		public int getOverflowPosition() {
			return overflowPosition;
		}

		public int getMatchCount() {
			return matchCount;
		}

		public void setMatchCount(int matchCount) {
			this.matchCount = matchCount;
		}

		public void reset() {
			overflowPosition = 0;
			matchCount = 0;
			decoder.reset();
		}

		public void append(IoBuffer in) {
			if (overflowPosition != 0) {
				discard(in);
			} else if (buf.position() > maxLineLength - in.remaining()) {
				overflowPosition = buf.position();
				buf.clear();
				discard(in);
			} else {
				getBuffer().put(in);
			}
		}

		private void discard(IoBuffer in) {
			if (Integer.MAX_VALUE - in.remaining() < overflowPosition) {
				overflowPosition = Integer.MAX_VALUE;
			} else {
				overflowPosition += in.remaining();
			}

			in.position(in.limit());
		}
	}
}