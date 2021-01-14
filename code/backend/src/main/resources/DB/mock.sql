insert into dictionary(uuid, name, url)
values ('01338a71-d551-483e-ad6c-c61808b7a3f3', 'dict_1', 'dict_1.dict'),
        ('b33257d2-066d-43b3-817b-7ff980f7c8a9', 'dict_2', 'dict_2.dict'),
        ('0ba07557-6553-4b66-94d7-92fc6c400296', 'dict_3', 'dict_3.dict'),
        ('25e7475e-893d-4435-9912-d8df94883ed3', 'dict_4', 'dict_4.dict');

insert into media(uuid, name, url, is_image)
values ('c31c7d29-fe9c-4aeb-8ad2-9358053c59b2', 'img_1', 'img_1.jpg', true),
       ('f44b20a5-6162-4c46-9b72-ec5499a55a21', 'img_2', 'img_2.jpg', true),
       ('d657935b-16a8-4ae1-8e5e-1519fb89ca5f', 'img_3', 'img_3.jpg', true),
       ('110040d9-22a9-4164-b06c-54fedf1a96cf', 'sound_1', 'sound_1.mp3', false),
       ('599733e2-2e1e-4331-bfe4-02264bfaf6d5', 'sound_2', 'sound_2.mp3', false),
       ('58037bce-c1a4-42f7-8e66-014e2a4d5fc5', 'sound_3', 'sound_3.mp3', false);

insert into question(answer, text, url, type)
values ('answer1', 'text1','img_1.jpg', 'image'),
       ('answer2', 'text2','img_2.jpg', 'image'),
       ('answer3', 'text3','img_3.jpg', 'image'),
       ('answer4', 'text4','sound_1.mp3', 'sound'),
       ('answer5', 'text5','sound_2.mp3', 'sound'),
       ('answer6', 'text6','sound_3.mp3', 'sound');

insert into "user"(uuid, login, password, is_admin)
values ('d08edab7-c811-412d-a1f9-a86b14980033', 'admin',
        '$2a$10$UcERjbTrFIZPOGHau.lroOfXzs3tA1TaNQW5SiFnKtnooFDLJ/XoC', true),
       ('7343824b-c93a-4e76-9e82-385b5f3e3020', 'user',
        '$2a$10$/9dXuG365lV0uGJtbiWJg.crOTE894cOxDB8xEBe3cRaWuHt7Bcr6', false);
