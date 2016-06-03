SELECT q3.them
FROM(SELECT tl.T_TITLE as them, count(rw.REVIEW_ID)
      FROM (SELECT pc.TITLE_ID as titID
            FROM  (SELECT pa.PUBLICATION_ID as pubID
                    FROM AUTHOR aut, PUBLICATION_AUTHORS pa
                    WHERE aut.AUT_NAME = 'Tracy Fobes'
                          and aut.AUT_ID = pa.AUTHOR_ID) q1 -- publication from author
            , PUBLICATION_CONTENT pc
            where pc.PUBLICATION_ID = q1.pubID) q2  -- title from said author
      , REVIEW rw, TITLE tl
      WHERE rw.TITLE_ID = q2.titID and tl.T_ID = q2.titID
      group by tl.T_TITLE
      order by -count(rw.REVIEW_ID) ) q3 -- give all title that review and there number sorted
WHERE ROWNUM < 6 -- we keep the five most reviewed