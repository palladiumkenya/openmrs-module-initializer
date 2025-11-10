package org.openmrs.module.initializer.api.queues;

import org.openmrs.annotation.OpenmrsProfile;
import org.openmrs.module.initializer.api.loaders.BaseCsvLoader;
import org.openmrs.module.queue.model.QueueRoom;
import org.springframework.beans.factory.annotation.Autowired;

@OpenmrsProfile(modules = { "queue:*" })
public class QueueRoomLoader extends BaseCsvLoader<QueueRoom, QueueRoomCsvParser> {
	
	@Autowired
	public void setParser(QueueRoomCsvParser parser) {
		this.parser = parser;
	}
}
