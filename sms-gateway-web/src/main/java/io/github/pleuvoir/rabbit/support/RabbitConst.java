package io.github.pleuvoir.rabbit.support;

public class RabbitConst {

	/** 开始 */
	public static class Begin {

		public static final String EXCHANGE 	= "x.begin";

		public static final String QUEUE 		= "q.begin";

		public static final String ROUTING_KEY 	= "r.begin";
	}

	/** 到达开始时间 （死信队列）*/
	public static class BeginArrival {

		public static final String EXCHANGE 	= "x.beginArrival";

		public static final String QUEUE 		= "q.beginArrival";

		public static final String ROUTING_KEY 	= "r.beginArrival";
	}

	
	public static class Normal {

		public static final String EXCHANGE 	= "x.normal";

		public static final String QUEUE 		= "q.normal";

		public static final String ROUTING_KEY 	= "r.normal";
	}
	
	/**
	 * 定时队列 （死信队列）
	 */
	public static class FixedTime {

		public static final String EXCHANGE 	= "x.fixedTime";

		public static final String QUEUE 		= "q.fixedTime";

		public static final String ROUTING_KEY 	= "r.fixedTime";
	}
	
}
