SELECT tit.T_TITLE  
FROM  (SELECT q1.title as theOne, count(q1.rew) + COUNT(q1.awd)
      FROM  (SELECT DISTINCT rw.TITLE_ID as title , rw.REVIEW_ID as rew, ta.AWARD_ID as awd
            FROM REVIEW rw , TITLE_AWARD ta
            WHERE rw.TITLE_ID = ta.TITLE_ID) q1
      GROUP BY q1.title
      ORDER BY count(q1.rew) + COUNT(q1.awd) DESC) q2, TITLE tit
WHERE ROWNUM < 4 and q2.theOne = tit.T_ID

