package io.github.pleuvoir.message.channel.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelServiceException extends Exception {

	private static final long serialVersionUID = 4035256482930362735L;

	public ChannelServiceException() {
		super();
	}

	public ChannelServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ChannelServiceException(String message) {
		super(message);
	}

	public ChannelServiceException(Throwable cause) {
		super(cause);
	}

}
