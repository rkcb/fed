package com.fed.data;

import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

final public class Roles implements GrantedAuthority {

    private static HashMap<Value, Roles> all;
    private String role;

    public enum Value {
        PLAYER,
        ADMIN
    }

    static {
        all = new HashMap<>(Value.values().length);
        all.put(Value.PLAYER, new Roles("Player"));
        all.put(Value.PLAYER, new Roles("Admin"));
    }

    private Roles(String role){ this.role = role; }

    @Override
    public String getAuthority() {
        return role;
    }

    public static Roles get(Value value){
        return all.get(value);
    }

    public static Collection<GrantedAuthority> of(Value... vals){
        return Arrays.stream(vals).map(v -> Roles.get(v))
                .collect(Collectors.toSet());
    }

    public static List<GrantedAuthority> listOf(Value... vals){
        return Arrays.stream(vals).map(Roles::get).collect(Collectors.toList());
    }

}