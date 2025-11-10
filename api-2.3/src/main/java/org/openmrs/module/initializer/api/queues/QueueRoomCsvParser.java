package org.openmrs.module.initializer.api.queues;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.annotation.OpenmrsProfile;
import org.openmrs.module.initializer.Domain;
import org.openmrs.module.initializer.api.BaseLineProcessor;
import org.openmrs.module.initializer.api.CsvLine;
import org.openmrs.module.initializer.api.CsvParser;
import org.openmrs.module.queue.api.QueueRoomService;
import org.openmrs.module.queue.model.QueueRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@OpenmrsProfile(modules = { "queue:*" })
public class QueueRoomCsvParser extends CsvParser<QueueRoom, BaseLineProcessor<QueueRoom>> {
	
	private final QueueRoomService queueRoomService;
	
	@Autowired
	public QueueRoomCsvParser(@Qualifier("queue.QueueRoomService") QueueRoomService queueRoomService,
	    QueueRoomLineProcessor processor) {
		super(processor);
		this.queueRoomService = queueRoomService;
	}
	
	@Override
	public Domain getDomain() {
		return Domain.QUEUE_ROOMS;
	}
	
	@Override
	public QueueRoom bootstrap(CsvLine line) throws IllegalArgumentException {
		String uuid = line.getUuid();
		QueueRoom queueRoom = queueRoomService.getQueueRoomByUuid(uuid).orElse(new QueueRoom());
		if (StringUtils.isNotBlank(uuid)) {
			queueRoom.setUuid(uuid);
		}
		return queueRoom;
	}
	
	@Override
	public QueueRoom save(QueueRoom instance) {
		return queueRoomService.saveQueueRoom(instance);
	}
}
