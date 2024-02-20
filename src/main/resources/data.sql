INSERT INTO category_group (name)
SELECT 'WBC' WHERE NOT EXISTS (
    SELECT 1 FROM category_group WHERE name = 'WBC'
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 0, 46.3, 'ATOM', category_group.id
FROM category_group
WHERE category_group.name = 'WBC'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'ATOM'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 46.31, 47.6, 'STRAW', category_group.id
FROM category_group
WHERE category_group.name = 'WBC'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'STRAW'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 47.61, 49.0, 'LIGHT FLY', category_group.id
FROM category_group
WHERE category_group.name = 'WBC'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'LIGHT FLY'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 49.1, 50.8, 'FLY', category_group.id
FROM category_group
WHERE category_group.name = 'WBC'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'FLY'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 50.81, 52.2, 'SUPER FLY', category_group.id
FROM category_group
WHERE category_group.name = 'WBC'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'SUPER FLY'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 52.21, 53.5, 'ROOSTER', category_group.id
FROM category_group
WHERE category_group.name = 'WBC'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'ROOSTER'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 53.51, 55.3, 'SUPER ROOSTER', category_group.id
FROM category_group
WHERE category_group.name = 'WBC'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'SUPER ROOSTER'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 55.31, 57.2, 'FEATHER', category_group.id
FROM category_group
WHERE category_group.name = 'WBC'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'FEATHER'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 57.21, 59.0, 'SUPER FEATHER', category_group.id
FROM category_group
WHERE category_group.name = 'WBC'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'SUPER FEATHER'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 59.01, 61.2, 'LIGHT', category_group.id
FROM category_group
WHERE category_group.name = 'WBC'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'LIGHT'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 61.21, 63.5, 'SUPER LIGHT', category_group.id
FROM category_group
WHERE category_group.name = 'WBC'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'SUPER LIGHT'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 63.51, 66.7, 'MIDDLE MEDIUM', category_group.id
FROM category_group
WHERE category_group.name = 'WBC'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'MIDDLE MEDIUM'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 66.71, 69.9, 'SUPER MIDDLE MEDIUM', category_group.id
FROM category_group
WHERE category_group.name = 'WBC'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'SUPER MIDDLE MEDIUM'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 69.91, 72.6, 'MEDIUM', category_group.id
FROM category_group
WHERE category_group.name = 'WBC'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'MEDIUM'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 72.61, 76.2, 'SUPER MEDIUM', category_group.id
FROM category_group
WHERE category_group.name = 'WBC'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'SUPER MEDIUM'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 76.21, 79.4, 'MIDDLE HEAVY', category_group.id
FROM category_group
WHERE category_group.name = 'WBC'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'MIDDLE HEAVY'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 79.41, 90.7, 'CRUISER', category_group.id
FROM category_group
WHERE category_group.name = 'WBC'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'CRUISER'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 90.71, 101.6, 'BRIDGE', category_group.id
FROM category_group
WHERE category_group.name = 'WBC'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'BRIDGE'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 101.61, 9999, 'HEAVY', category_group.id
FROM category_group
WHERE category_group.name = 'WBC'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'HEAVY'
      AND category_group_id = category_group.id
);


INSERT INTO category_group (name)
SELECT 'WBA' WHERE NOT EXISTS (
    SELECT 1 FROM category_group WHERE name = 'WBA'
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 0, 46.3, 'LIGHT STRAW', category_group.id
FROM category_group
WHERE category_group.name = 'WBA'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'LIGHT STRAW'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 46.31, 47.6, 'STRAW', category_group.id
FROM category_group
WHERE category_group.name = 'WBA'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'STRAW'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 47.61, 49.0, 'LIGHT FLY', category_group.id
FROM category_group
WHERE category_group.name = 'WBA'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'LIGHT FLY'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 49.1, 50.8, 'FLY', category_group.id
FROM category_group
WHERE category_group.name = 'WBA'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'FLY'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 50.81, 52.2, 'SUPER FLY', category_group.id
FROM category_group
WHERE category_group.name = 'WBA'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'SUPER FLY'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 52.21, 53.5, 'ROOSTER', category_group.id
FROM category_group
WHERE category_group.name = 'WBA'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'ROOSTER'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 53.51, 55.3, 'SUPER ROOSTER', category_group.id
FROM category_group
WHERE category_group.name = 'WBA'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'SUPER ROOSTER'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 55.31, 57.2, 'FEATHER', category_group.id
FROM category_group
WHERE category_group.name = 'WBA'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'FEATHER'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 57.21, 59.0, 'SUPER FEATHER', category_group.id
FROM category_group
WHERE category_group.name = 'WBA'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'SUPER FEATHER'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 59.01, 61.2, 'LIGHT', category_group.id
FROM category_group
WHERE category_group.name = 'WBA'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'LIGHT'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 61.21, 63.5, 'SUPER LIGHT', category_group.id
FROM category_group
WHERE category_group.name = 'WBA'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'SUPER LIGHT'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 63.51, 66.7, 'MIDDLE MEDIUM', category_group.id
FROM category_group
WHERE category_group.name = 'WBA'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'MIDDLE MEDIUM'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 66.71, 69.9, 'SUPER MIDDLE MEDIUM', category_group.id
FROM category_group
WHERE category_group.name = 'WBA'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'SUPER MIDDLE MEDIUM'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 69.91, 72.6, 'MEDIUM', category_group.id
FROM category_group
WHERE category_group.name = 'WBA'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'MEDIUM'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 72.61, 76.2, 'SUPER MEDIUM', category_group.id
FROM category_group
WHERE category_group.name = 'WBA'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'SUPER MEDIUM'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 76.21, 79.4, 'MIDDLE HEAVY', category_group.id
FROM category_group
WHERE category_group.name = 'WBA'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'MIDDLE HEAVY'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 79.41, 90.7, 'CRUISER', category_group.id
FROM category_group
WHERE category_group.name = 'WBA'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'CRUISER'
      AND category_group_id = category_group.id
);

INSERT INTO category (min_weight, max_weight, category_name, category_group_id)
SELECT 90.71, 9999, 'HEAVY', category_group.id
FROM category_group
WHERE category_group.name = 'WBA'
  AND NOT EXISTS (
    SELECT 1 FROM category
    WHERE category_name = 'HEAVY'
      AND category_group_id = category_group.id
);
