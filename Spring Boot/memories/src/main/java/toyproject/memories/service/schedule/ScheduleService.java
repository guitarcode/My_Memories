package toyproject.memories.service.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.memories.domain.schedule.*;
import toyproject.memories.domain.user.User;
import toyproject.memories.dto.schedule.ScheduleReturnDto;
import toyproject.memories.repository.UserRepository;
import toyproject.memories.repository.schedule.ScheduleRepository;
import toyproject.memories.repository.schedule.ScheduleStorageRepository;
import toyproject.memories.repository.schedule.WeeklyScheduleCreateDto;
import toyproject.memories.service.CreateMap;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleStorageRepository scheduleStorageRepository;
    private final UserRepository userRepository;

    @Transactional
    public Map<String,Object> createWeeklySchedule(WeeklyScheduleCreateDto weeklyScheduleCreateDto, String username){
        User user = userRepository.findByName(username).orElse(null);

        ScheduleStorage scheduleStorage = scheduleStorageRepository.findOne(
                weeklyScheduleCreateDto.getStorageId(),user
        ).stream().findAny().orElse(null);

        List<Schedule> schedules = new ArrayList<>();

        if(scheduleStorage != null){
            LocalDate start = LocalDate.parse(weeklyScheduleCreateDto.getStartDay());
            LocalDateTime end = LocalDate.parse(weeklyScheduleCreateDto.getEndDay()).atTime(23,59);
            DayOfWeek startDay = start.getDayOfWeek();

            for(ScheduleItem item : scheduleStorage.getScheduleItems()){
                long itemStartDay = item.getStartDay().getValue();
                long itemEndDay = item.getEndDay().getValue();

                //(아이템의 요일) - (요청받은 시작일의 요일) 만큼 시작날짜에 더해서 요일을 맞춤
                LocalDate startDate = start.plusDays((itemStartDay+7-startDay.getValue()) % 7);
                //(아이템의 end 요일) - (아이템의 start 요일)로 요일을 맞춤
                LocalDate endDate = startDate.plusDays(itemEndDay-itemStartDay);

                LocalDateTime itemStartDateTime = startDate.atTime(item.getStartTime());
                LocalDateTime itemEndDateTime = endDate.atTime(item.getEndTime());



                while(itemStartDateTime.isBefore(end)){
                    if(scheduleRepository.isOverlap(itemStartDateTime,itemEndDateTime)) {
                        itemStartDateTime = itemStartDateTime.plusDays(7L);
                        itemEndDateTime = itemEndDateTime.plusDays(7L);
                        continue;
                    }
                    Schedule schedule = Schedule.builder()
                            .title(item.getTitle())
                            .subTitle(scheduleStorage.getTitle())
                            .startDateTime(itemStartDateTime)
                            .endDateTime(itemEndDateTime)
                            .user(user)
                            .importance(item.getImportance())
                            .scheduleStorage(scheduleStorage)
                            .build();
                    schedules.add(schedule);
                    itemStartDateTime = itemStartDateTime.plusDays(7L);
                    itemEndDateTime = itemEndDateTime.plusDays(7L);
                }
            }
            return CreateMap.successMap(scheduleRepository.saveSchedules(schedules));
        }
        else{
            return CreateMap.failMap("Bad Request");
        }
    }


    public List<ScheduleReturnDto> scheduleList(String username){
        User user = userRepository.findByName(username).orElse(null);

        return scheduleRepository.findAll(user).stream().map(ScheduleReturnDto::new).collect(Collectors.toList());
    }

}
