SELECT *
FROM (SELECT tii.T_TITLE as tit, count(tii.T_TITLE) as nbTrad, tii.LANGUAGE_ID as lang, 
      ROW_NUMBER () OVER (PARTITION BY tii.LANGUAGE_ID
                    ORDER BY count(tii.T_TITLE) DESC) as rec
      FROM TITLE ti, TITLE tii
      WHERE ti.PARENT !=0 and ti.PARENT = tii.T_ID and tii.LANGUAGE_ID = ti.LANGUAGE_ID
      group by tii.T_TITLE, tii.LANGUAGE_ID
      order by -count(tii.T_TITLE)) q1
WHERE rec <= 3

