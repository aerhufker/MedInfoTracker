package com.medinfotracker.medinfotracker.models;

// <<<<<<< MTW1

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity {


    @Id
    @GeneratedValue
    private int id;
// <<<<<<< MTW1

//    getters and setters

    public int getId() { return id; }
// =======
    /**
     * get and set
     */
//     public int getId() {
//         return id;
//     }

// >>>>>>> merging

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
// <<<<<<< MTW1
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity that = (AbstractEntity) o;
        return id == that.id;
// =======
//         if (!(o instanceof AbstractEntity)) return false;

//         AbstractEntity that = (AbstractEntity) o;

//         return getId() == that.getId();
// >>>>>>> merging
    }

    @Override
    public int hashCode() {
// <<<<<<< MTW1
        return Objects.hash(id);
    }

}
// =======
//         return getId();
//     }
// }
// >>>>>>> merging
