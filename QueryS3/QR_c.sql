SELECT him
FROM (SELECT auth.AUT_NAME as him , count(awid)
      FROM (SELECT pa.AUTHOR_ID as autid, ta.AWARD_ID as awid
            FROM PUBLICATION_CONTENT pc, PUBLICATION_AUTHORS pa, TITLE_AWARD ta
            WHERE ta.TITLE_ID = pc.TITLE_ID and pc.PUBLICATION_ID = pa.PUBLICATION_ID) q1 -- get all author and their awards
      , AUTHOR auth, AWARDS aw
      WHERE autid = auth.AUT_ID and awid = aw.AW_ID and auth.AUT_DEATHDATE IS NOT NULL -- match all q1 with aw date and deathdate
                    and TO_NUMBER(SUBSTR(auth.AUT_DEATHDATE, 1, 4), '9999') < TO_NUMBER(SUBSTR(aw.AW_DATE, 1, 4), '9999') -- since we have only years for trophy we look that the deathdate is smaller than the aw date
      GROUP BY auth.AUT_NAME -- group by author name
      ORDER BY -count(awid))q2 -- sort from most aw to less
WHERE ROWNUM < 2 -- only the first !