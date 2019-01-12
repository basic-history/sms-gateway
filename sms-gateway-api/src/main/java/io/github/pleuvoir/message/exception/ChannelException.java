package io.github.pleuvoir.message.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelException extends Exception {

	private static final long serialVersionUID = 4035256482930362735L;

	public ChannelException() {
		super();
	}

	public ChannelException(String message, Throwable cause) {
		super(message, cause);
	}

	public ChannelException(String message) {
		super(message);
	}

	public ChannelException(Throwable cause) {
		super(cause);
	}

}
