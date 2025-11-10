package org.openmrs.module.initializer.api.queues;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.annotation.OpenmrsProfile;
import org.openmrs.api.ConceptService;
import org.openmrs.module.initializer.api.BaseLineProcessor;
import org.openmrs.module.initializer.api.CsvLine;
import org.openmrs.module.queue.api.QueueService;
import org.openmrs.module.queue.model.Queue;
import org.openmrs.module.queue.model.QueueRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * This is the first level line processor for a Queue room
 */
@OpenmrsProfile(modules = { "queue:*" })
public class QueueRoomLineProcessor extends BaseLineProcessor<QueueRoom> {
	
	protected static String HEADER_SERVICE = "service";
	
	protected static String HEADER_QUEUE = "queue";
	
	private final ConceptService conceptService;
	
	private final QueueService queueService;
	
	@Autowired
	public QueueRoomLineProcessor(@Qualifier("conceptService") ConceptService conceptService,
	    @Qualifier("queue.QueueService") QueueService queueService) {
		super();
		this.conceptService = conceptService;
		this.queueService = queueService;
	}
	
	@Override
	public QueueRoom fill(QueueRoom queueRoom, CsvLine line) throws IllegalArgumentException {
		queueRoom.setName(line.get(HEADER_NAME, true));
		queueRoom.setDescription(line.getString(HEADER_DESC));
		
		if (line.containsHeader(HEADER_QUEUE)) {
			String queue = line.getString(HEADER_QUEUE);
			if (StringUtils.isNotBlank(queue)) {
				queueRoom.setQueue(fetchQueue(queue, queueService));
			} else {
				queueRoom.setQueue(null);
			}
		}
		return queueRoom;
	}
	
	/**
	 * Fetches a queue trying various routes for its "id".
	 * 
	 * @param id The queue name or UUID.
	 * @param service
	 * @return The {@link Queue} instance if found, null otherwise.
	 */
	public static Queue fetchQueue(String id, QueueService service) {
		return service.getQueueByUuid(id).orElseThrow(() -> new IllegalArgumentException("Queue not found for id: " + id));
	}
}
