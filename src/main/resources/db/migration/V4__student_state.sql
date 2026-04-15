CREATE TABLE user_course_enrollments (
                                         user_id     UUID      NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                                         course_id   UUID      NOT NULL REFERENCES courses(id) ON DELETE CASCADE,
                                         enrolled_at TIMESTAMP NOT NULL DEFAULT NOW(),
                                         PRIMARY KEY (user_id, course_id)
);

CREATE TYPE node_session_type AS ENUM ('PRACTICE', 'PRETEST', 'CLUSTER_CHECKPOINT');

CREATE TABLE user_ability (
                              id         UUID             PRIMARY KEY DEFAULT gen_random_uuid(),
                              user_id    UUID             NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                              course_id  UUID             NOT NULL REFERENCES courses(id) ON DELETE CASCADE,
                              theta      DOUBLE PRECISION NOT NULL DEFAULT 0.0,
                              updated_at TIMESTAMP        NOT NULL DEFAULT NOW(),
                              UNIQUE (user_id, course_id)
);

CREATE TABLE user_node_states (
                                  id              UUID             PRIMARY KEY DEFAULT gen_random_uuid(),
                                  user_id         UUID             NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                                  node_id         UUID             NOT NULL REFERENCES knowledge_nodes(id) ON DELETE CASCADE,
                                  p_mastered      DOUBLE PRECISION NOT NULL DEFAULT 0.0,
                                  is_mastered     BOOLEAN          NOT NULL DEFAULT FALSE,
                                  is_skipped      BOOLEAN          NOT NULL DEFAULT FALSE,
                                  last_visited_at TIMESTAMP,
                                  UNIQUE (user_id, node_id)
);