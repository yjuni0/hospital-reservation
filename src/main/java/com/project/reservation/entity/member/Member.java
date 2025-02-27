package com.project.reservation.entity.member;

import com.project.reservation.common.BaseTimeEntity;
import com.project.reservation.entity.onlineConsult.Question;
import com.project.reservation.entity.onlineConsult.Answer;
import com.project.reservation.entity.customerReviews.Comment;
import com.project.reservation.entity.customerReviews.Review;
import com.project.reservation.entity.customerReviews.ReviewLike;
import com.project.reservation.entity.notice.Notice;
import com.project.reservation.entity.onlineReserve.Reservation;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseTimeEntity implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 5)
    private String name;

    @Column(nullable = false,unique = true, length = 10)
    private String nickName;

    @Column(nullable = false,length = 50)
    private String email;

    @Column(length = 500)
    private String password;

    @Column(length = 50)
    private String addr;

    private String birth;

    @Column(length = 13,unique = true)
    private String phoneNum;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role roles;

    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Question> questions=new ArrayList<>();

    @OneToMany(mappedBy = "admin",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Answer> answers=new ArrayList<>();

    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments=new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviews=new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Pet> pets=new ArrayList<>();

    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reservation> reservations=new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ReviewLike> likes=new ArrayList<>();
    @Builder
    public Member(Long id, String name, String nickName, String email, String password, String addr, String birth, String phoneNum, Role roles) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.addr = addr;
        this.birth = birth;
        this.phoneNum = phoneNum;
        this.roles = roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.roles.name()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }
}
