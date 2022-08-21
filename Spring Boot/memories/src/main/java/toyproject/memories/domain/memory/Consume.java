package toyproject.memories.domain.memory;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "C")
public class Consume extends Activity{
    private int consumeAmount;

}
