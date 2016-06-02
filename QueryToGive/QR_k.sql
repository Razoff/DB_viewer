SELECT ROUND(AVG(q1.nb),2) as nbPub -- average them 
FROM  (SELECT pub.PUBLICATION_SERIES_ID, count(pub.PUBLICATION_SERIES_ID) as nb
      FROM PUBLICATIONS pub
      WHERE pub.PUBLICATION_SERIES_ID IS NOT NULL -- different number of pub
      GROUP BY pub.PUBLICATION_SERIES_ID) q1 -- group them