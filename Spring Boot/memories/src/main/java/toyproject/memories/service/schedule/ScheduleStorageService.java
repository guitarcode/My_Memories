package toyproject.memories.service.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.memories.domain.schedule.Property;
import toyproject.memories.domain.schedule.ScheduleItem;
import toyproject.memories.domain.schedule.ScheduleStorage;
import toyproject.memories.domain.user.User;
import toyproject.memories.dto.schedule.ScheduleItemCreateAndReturnDto;
import toyproject.memories.dto.schedule.ScheduleStorageCreateDto;
import toyproject.memories.dto.schedule.ScheduleStorageReturnDto;
import toyproject.memories.repository.UserRepository;
import toyproject.memories.repository.schedule.ScheduleStorageRepository;

import java.time.DayOfWeek;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleStorageService {

    private final ScheduleStorageRepository scheduleStorageRepository;
    private final UserRepository userRepository;

    @Transactional
    public ScheduleStorageReturnDto createScheduleStorage(ScheduleStorageCreateDto scheduleStorageCreateDto){

//        User user = userRepository.findByName(username).orElse(null);

        System.out.println("여기까진 잘됨");

        ScheduleStorage scheduleStorage = new ScheduleStorage(
                scheduleStorageCreateDto.getName(),
                scheduleStorageCreateDto.getSubName());

        System.out.println("여기까진 잘됨");

        for(ScheduleItemCreateAndReturnDto scheduleItem : scheduleStorageCreateDto.getScheduleItems()){
            ScheduleItem scheduleItemEntity = ScheduleItem.builder()
                    .startTime(scheduleItem.getStartTime())
                    .endTime(scheduleItem.getEndTime())
                    .dayOfWeek(DayOfWeek.valueOf(scheduleItem.getDayOfWeek()))
                    .property(Property.valueOf(scheduleItem.getProperty()))
                    .build();

            scheduleStorage.addScheduleItem(scheduleItemEntity);
        }

        System.out.println("여기까진 잘됨");

        return scheduleStorageRepository.save(scheduleStorage);
    }
}
