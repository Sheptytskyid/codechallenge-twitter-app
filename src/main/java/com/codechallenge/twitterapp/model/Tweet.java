package com.codechallenge.twitterapp.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "tweets")
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Tweet {
    @Id
    @GeneratedValue
    Long id;
    @Column
    String text;
    @Column(name = "create_date")
    LocalDateTime timeStamp;
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "USER_ID_FK"))
    User author;

    public Tweet(String text, LocalDateTime timeStamp, User author) {
        this.text = text;
        this.timeStamp = timeStamp;
        this.author = author;
    }
}
