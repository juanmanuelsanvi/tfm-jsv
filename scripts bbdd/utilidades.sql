-- NODOS
SELECT * FROM PERSONA p 

-- ARISTAS
SELECT 
	p.NOMBRE ,  m.* 
FROM 
	MENSAJE m
INNER JOIN 
	PERSONA p
ON
	m.IDPERSONA = p.ID 
	

	SELECT COUNT(*) FROM MENSAJE m WHERE IDMENSAJEPADRE IS NULL
	
	SELECT * FROM MENSAJE m	

	drop view vAristas 
	
CREATE VIEW vAristas AS 	
SELECT 
	mSource.IDPERSONA AS source,
	mSource.IDFORO AS foro,
	mat.IDASIGNATURA as asignatura, 
	mSource.numcaracteres,
	mSource.fechaenvio,
	mTarget.IDPERSONA AS target
FROM 
	MENSAJE mSource
INNER JOIN 
	MENSAJE mTarget
ON
	mSource.IDMENSAJEPADRE = mTarget .ID
INNER JOIN FORO foro  
ON
	mSource.IDFORO = foro.ID
INNER JOIN MATERIA mat 
ON
	foro.IDMATERIA = mat.ID 
	

	
SELECT * FROM vAristas 
	
	

