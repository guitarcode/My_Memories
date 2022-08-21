package toyproject.memories.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalTime;

@Entity
public class ScheduleInventory {
    @Id
    @GeneratedValue
    @Column(name = "schedule_inventory_id")
    private Long id;

    private LocalTime startTime;
    private LocalTime endTime;

}
