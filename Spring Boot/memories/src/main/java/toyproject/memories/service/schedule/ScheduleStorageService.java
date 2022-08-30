package toyproject.memories.service.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.memories.domain.schedule.DayOfWeek;
import toyproject.memories.domain.schedule.ScheduleItem;
import toyproject.memories.domain.schedule.ScheduleStorage;
import toyproject.memories.domain.user.User;
import toyproject.memories.dto.schedule.ScheduleItemCreateDto;
import toyproject.memories.dto.schedule.ScheduleStorageCreateDto;
import toyproject.memories.dto.schedule.ScheduleStorageListDto;
import toyproject.memories.dto.schedule.ScheduleStorageReturnDto;
import toyproject.memories.repository.UserRepository;
import toyproject.memories.repository.schedule.ScheduleStorageRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleStorageService {

    private final ScheduleStorageRepository scheduleStorageRepository;
    private final UserRepository userRepository;

    @Transactional
    public ScheduleStorageReturnDto createScheduleStorage(ScheduleStorageCreateDto scheduleStorageCreateDto, String username){

        User user = userRepository.findByName(username).orElse(null);


        ScheduleStorage scheduleStorage = new ScheduleStorage(
                scheduleStorageCreateDto.getName(),
                scheduleStorageCreateDto.getSubName(),
                user);


        for(ScheduleItemCreateDto scheduleItem : scheduleStorageCreateDto.getScheduleItems()){
            System.out.println("스케쥴 시작일:" + scheduleItem.getStartDay());
            ScheduleItem scheduleItemEntity = ScheduleItem.builder()
                    .name(scheduleItem.getName())
                    .startTime(scheduleItem.startTimeParse())
                    .endTime(scheduleItem.endTimeParse())
                    .startDay(DayOfWeek.valueOf(scheduleItem.getStartDay()))
                    .endDay(DayOfWeek.valueOf(scheduleItem.getEndDay()))
//                    .property(Property.valueOf(scheduleItem.getProperty()))
                    .build();

            scheduleStorage.addScheduleItem(scheduleItemEntity);
        }

        return scheduleStorageRepository.save(scheduleStorage);
    }

    public List<ScheduleStorageListDto> storageList(String username){
        User user = userRepository.findByName(username).orElse(null);
        return scheduleStorageRepository.findAllByUser(user);
    }
}
