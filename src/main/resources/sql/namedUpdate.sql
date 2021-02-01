update
    m_user
set
    password = :password,
    user_name = :userName,
    birthday = :birthday,
    age = :age,
    marriage = :marriage,
where
    user_id = :userId