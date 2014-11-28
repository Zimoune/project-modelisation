DROP TABLE IF EXISTS Models;
DROP TABLE IF EXISTS KeyWords;
DROP TABLE IF EXISTS KeyWordAssociation;

CREATE TABLE Models(
        idModel INTEGER PRIMARY KEY ,
        name    Varchar (30) NOT NULL ,
        author  Varchar (30) NOT NULL ,
        model   Clob NOT NULL ,
		previewImagePath Varchar(50) NOT NULL
);


CREATE TABLE KeyWords(
        keyWord Varchar (30) NOT NULL ,
        PRIMARY KEY (keyWord )
);


CREATE TABLE KeyWordAssociation(
        idModel INTEGER NOT NULL ,
        keyWord Varchar (30) NOT NULL ,
        PRIMARY KEY (idModel ,keyWord ), 
		FOREIGN KEY (idModel) REFERENCES Models(idModel),
		FOREIGN KEY (keyWord) REFERENCES KeyWords(keyWord)
);
