package toyproject.memories.service.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.memories.domain.schedule.Property;
import toyproject.memories.domain.schedule.ScheduleItem;
import toyproject.memories.domain.user.User;
import toyproject.memories.dto.schedule.ScheduleItemCreateDto;
import toyproject.memories.repository.UserRepository;
import toyproject.memories.repository.schedule.ScheduleItemRepository;

import java.time.DayOfWeek;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleItemService {

    private final ScheduleItemRepository scheduleItemRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createScheduleItem(ScheduleItemCreateDto scheduleItemCreateDto, String username){

        User user = userRepository.findByName(username).orElse(null);

        ScheduleItem scheduleItem = ScheduleItem.builder()
                .name(scheduleItemCreateDto.getName())
                .subName(scheduleItemCreateDto.getSubName())
                .startTime(scheduleItemCreateDto.getStartTime())
                .endTime(scheduleItemCreateDto.getEndTime())
                .dayOfWeek(DayOfWeek.valueOf(scheduleItemCreateDto.getDayOfWeek()))
                .property(Property.valueOf(scheduleItemCreateDto.getProperty()))
                .user(user)
                .build();

        return scheduleItemRepository.save(scheduleItem);
    }
}
