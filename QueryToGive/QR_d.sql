SELECT q.one
FROM (SELECT ps.PUB_NAME as one , count(pub.P_ID)
      FROM PUBLICATIONS pub, PUBLISHER ps
      WHERE pub.PUBLISHER_ID = ps.PUB_ID
            and TO_NUMBER(SUBSTR(pub.P_DATE, 1, 4), '9999') = 2012 -- given year here
      GROUP BY ps.PUB_NAME
      ORDER BY -count(pub.P_ID)) q 
      -- this give sus the ordered list of the number of publisher for the year
WHERE ROWNUM < 2 -- only keep the biggest num