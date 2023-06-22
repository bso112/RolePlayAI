package com.bso112.domain

import java.util.UUID

data class Profile(
    val id: String,
    val thumbnail: String,
    val name: String
)

/**
 * Chat에 Profile을 포함하려 했으나, ChatEntity -> Chat 객체를 만들때가 문제다.
 * ChatEntity에는 Profile에 대한 모든 정보를 담지 않기에 Profile 객체를 만들 수 업다.
 * 따라서 Chat은 Profile의 일부 속성만 가지고 있다.
 */
fun Profile.createChat(message: String) = Chat(
    id = UUID.randomUUID().toString(),
    profileId = id,
    thumbnail = thumbnail,
    name = name,
    message = message
)
