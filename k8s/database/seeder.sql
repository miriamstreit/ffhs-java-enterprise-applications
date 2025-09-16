/* Truncate all Tables */

DO $$ DECLARE
    r RECORD;
BEGIN
    FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = current_schema()) LOOP
            EXECUTE 'TRUNCATE TABLE ' || quote_ident(r.tablename) || ' RESTART identity CASCADE';
        END LOOP;
END $$;

INSERT INTO public.ingredient
VALUES (1, 'Hopfen');

INSERT INTO public.ingredient
VALUES (2, 'Malz');

INSERT INTO public.beer (beer_id, name, image_url, price, stock)
VALUES (1, 'Zwickel', 'zwickel.png', 7.50, 2);

INSERT INTO public.beer (beer_id, name, image_url, price, stock)
VALUES (2, 'Stout', 'stout.png', 9.50, 3);

INSERT INTO public.beer (beer_id, name, image_url, price, stock)
VALUES (3, 'Irish Pale Ale', 'irish.png', 6.80, 2);

INSERT INTO public.beer (beer_id, name, image_url, price, stock)
VALUES (4, 'Bock', 'bock.png', 8, 2);


INSERT INTO public.beer_ingredients (beer_beer_id, ingredient_id, amount)
VALUES (
           (select beer_id from public.beer where name = 'Zwickel'),
           (select ingredient_id from public.ingredient where name = 'Hopfen'),
           10
       );

INSERT INTO public.beer_ingredients (beer_beer_id, ingredient_id, amount)
VALUES (
           (select beer_id from public.beer where name = 'Stout'),
           (select ingredient_id from public.ingredient where name = 'Hopfen'),
           10
       );

INSERT INTO public.beer_ingredients (beer_beer_id, ingredient_id, amount)
VALUES (
           (select beer_id from public.beer where name = 'Irish Pale Ale'),
           (select ingredient_id from public.ingredient where name = 'Hopfen'),
           10
       );

INSERT INTO public.beer_ingredients (beer_beer_id, ingredient_id, amount)
VALUES (
           (select beer_id from public.beer where name = 'Bock'),
           (select ingredient_id from public.ingredient where name = 'Hopfen'),
           10
       );

INSERT INTO public.beer_ingredients (beer_beer_id, ingredient_id, amount)
VALUES (
           (select beer_id from public.beer where name = 'Zwickel'),
           (select ingredient_id from public.ingredient where name = 'Malz'),
           15
       );