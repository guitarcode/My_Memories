package toyproject.memories.service.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.memories.domain.schedule.DayOfWeek;
import toyproject.memories.domain.schedule.Property;
import toyproject.memories.domain.schedule.ScheduleItem;
import toyproject.memories.domain.schedule.ScheduleStorage;
import toyproject.memories.domain.user.User;
import toyproject.memories.dto.schedule.ScheduleItemCreateAndReturnDto;
import toyproject.memories.dto.schedule.ScheduleStorageCreateDto;
import toyproject.memories.dto.schedule.ScheduleStorageReturnDto;
import toyproject.memories.repository.UserRepository;
import toyproject.memories.repository.schedule.ScheduleStorageRepository;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleStorageService {

    private final ScheduleStorageRepository scheduleStorageRepository;
    private final UserRepository userRepository;

    @Transactional
    public ScheduleStorageReturnDto createScheduleStorage(ScheduleStorageCreateDto scheduleStorageCreateDto){

//        User user = userRepository.findByName(username).orElse(null);


        ScheduleStorage scheduleStorage = new ScheduleStorage(
                scheduleStorageCreateDto.getName(),
                scheduleStorageCreateDto.getSubName());


        for(ScheduleItemCreateAndReturnDto scheduleItem : scheduleStorageCreateDto.getScheduleItems()){
            ScheduleItem scheduleItemEntity = ScheduleItem.builder()
                    .startTime(scheduleItem.startTimeParse())
                    .endTime(scheduleItem.endTimeParse())
                    .startDay(DayOfWeek.valueOf(scheduleItem.getStartDay()))
                    .endDay(DayOfWeek.valueOf(scheduleItem.getEndDay()))
                    .property(Property.valueOf(scheduleItem.getProperty()))
                    .build();

            scheduleStorage.addScheduleItem(scheduleItemEntity);
        }

        return scheduleStorageRepository.save(scheduleStorage);
    }
}
