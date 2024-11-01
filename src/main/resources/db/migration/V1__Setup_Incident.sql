CREATE TABLE IF NOT EXISTS incident
(
    id             uuid         PRIMARY KEY,
    occurring_time timestamp    NOT NULL,
    raiser         varchar(64)  NOT NULL,
    status         int          NOT NULL,
    resolution     int          NULL,
    title          varchar(128) NULL,
    description    text         NULL,
    create_time    timestamp    NOT NULL DEFAULT now(),
    last_update    timestamp    NOT NULL DEFAULT now(),
    version        integer      NOT NULL DEFAULT 0
);

CREATE INDEX idx_incident_raiser ON incident(raiser);
CREATE INDEX idx_incident_occurring_time ON incident(occurring_time);
