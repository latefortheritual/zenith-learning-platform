CREATE TABLE items (
                       id            UUID             PRIMARY KEY DEFAULT gen_random_uuid(),
                       node_id       UUID             NOT NULL REFERENCES knowledge_nodes(id) ON DELETE CASCADE,
                       question_text TEXT             NOT NULL,
                       beta          DOUBLE PRECISION NOT NULL DEFAULT 0.0
);

CREATE TABLE answer_options (
                                id            UUID    PRIMARY KEY DEFAULT gen_random_uuid(),
                                item_id       UUID    NOT NULL REFERENCES items(id) ON DELETE CASCADE,
                                option_text   TEXT    NOT NULL,
                                is_correct    BOOLEAN NOT NULL DEFAULT FALSE,
                                feedback_text TEXT
);