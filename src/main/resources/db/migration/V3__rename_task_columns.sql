-- Переименование колонок в таблице tasks
-- name -> task_name (наименование задачи)
-- description -> stage_name (наименование этапа)

ALTER TABLE tasks RENAME COLUMN name TO task_name;
ALTER TABLE tasks RENAME COLUMN description TO stage_name;
