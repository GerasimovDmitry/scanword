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

insert into question(uuid, answer, text, url, type)
values ('4fc35e7e-1cc0-45ad-80eb-6ebc80d3fe3e','answer1', 'text1','img_1.jpg', 'image'),
       ('2795f1cd-284a-47aa-a200-5c2e364e4420','answer2', 'text2','img_2.jpg', 'image'),
       ('cae06e9d-b535-4990-9511-6dd40425156d','answer3', 'text3','img_3.jpg', 'image'),
       ('b274dcb0-46b0-43e1-95bf-2df89e350c7a','answer4', 'text4','sound_1.mp3', 'sound'),
       ('55565834-b579-4096-80d4-775f11d5a6f8','answer5', 'text5','sound_2.mp3', 'sound'),
       ('6b7d181a-eb66-4579-a017-4eb6e85a498f','answer6', 'text6','sound_3.mp3', 'sound');

insert into "user"(uuid, login, password, is_admin)
values ('d08edab7-c811-412d-a1f9-a86b14980033', 'admin',
        '$2a$10$UcERjbTrFIZPOGHau.lroOfXzs3tA1TaNQW5SiFnKtnooFDLJ/XoC', true),
       ('7343824b-c93a-4e76-9e82-385b5f3e3020', 'user',
        '$2a$10$/9dXuG365lV0uGJtbiWJg.crOTE894cOxDB8xEBe3cRaWuHt7Bcr6', false);
