INSERT INTO "user" (nickname, name, email)
VALUES ('user1', 'John Doe', 'john.doe@example.com'),
       ('user2', 'Jane Doe', 'jane.doe@example.com'),
       ('user3', 'Alice Smith', 'alice.smith@example.com'),
       ('user4', 'Bob Johnson', 'bob.johnson@example.com'),
       ('user5', 'Eve Brown', 'eve.brown@example.com');

INSERT INTO category (title)
VALUES ('Technology'),
       ('Science'),
       ('Travel'),
       ('Food'),
       ('Sports');

INSERT INTO channel (title, description, image, author_id, create_date, category_id, language)
VALUES ('Tech News', 'Latest tech updates', 'tech_image.jpg', 1, '2023-01-01', 1, 'ru'),
       ('Space Explorers', 'Discover the universe', 'space_image.jpg', 2, '2023-02-01', 2, 'eng'),
       ('Adventure Travel', 'Explore the world', 'travel_image.jpg', 3, '2023-03-01', 3, 'ua'),
       ('Foodie Delights', 'Delicious recipes', 'food_image.jpg', 4, '2023-04-01', 4, 'ru'),
       ('Sports Mania', 'Exciting sports coverage', 'sports_image.jpg', 5, '2023-05-01', 5, 'eng');

INSERT INTO user_channel_subscription (channel_id, user_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5);