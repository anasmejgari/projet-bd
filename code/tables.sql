-- -----------------------------------------------------
-- Table Utilisateur
-- -----------------------------------------------------

-- DROP TABLE  AssocFilmCat;
-- DROP TABLE  AssocAlbumCat;
-- DROP TABLE  AssocPisteCat;
-- DROP TABLE  CategorieMusique;
-- DROP TABLE  CategorieFilm;
-- DROP TABLE  ClientFlux;
-- DROP TABLE  Client;
-- DROP TABLE  FichierFilm;
-- DROP TABLE  aPourRole;
-- DROP TABLE  Film;
-- DROP TABLE  FichierPiste;
-- DROP TABLE  PisteMusicale;
-- DROP TABLE  Album;
-- DROP TABLE  Biographie;
-- DROP TABLE  aPourDateDeNaissance;
-- DROP TABLE  Artiste;
-- DROP TABLE  DateNaissance;
-- DROP TABLE  FluxTexte;
-- DROP TABLE  FluxAudio ;
-- DROP TABLE  FluxVideo ;
-- DROP TABLE  Flux ;
-- DROP TABLE  CodecTexte;
-- DROP TABLE  CodecVideo;
-- DROP TABLE  CodecAudio;
-- DROP TABLE  Codec;
-- DROP TABLE  Fichier ;
-- DROP TABLE  Utilisateur ;
-- DROP TABLE  Langue;


-- -----------------------------------------------------
-- Table Langue
-- -----------------------------------------------------

CREATE TABLE Langue(
    Nom_langue VARCHAR(15) PRIMARY KEY
);



-- -----------------------------------------------------
-- Table Utilisateur
-- -----------------------------------------------------
CREATE TABLE Utilisateur(
    Mail  VARCHAR(120) PRIMARY KEY NOT NULL,
    Nom VARCHAR(25)NOT NULL,
    Prenom VARCHAR(25) NOT NULL,
    AGE INT NOT NULL,
    CodeAcces NUMBER(4),
    LangueUtilisateur VARCHAR(15) NOT NULL,
    CONSTRAINT fk_User_Language
       FOREIGN KEY (LangueUtilisateur)
       REFERENCES Langue (Nom_Langue)
);

-- -----------------------------------------------------
-- Table Fichier
-- -----------------------------------------------------

CREATE TABLE Fichier(
    idFichier NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    Taille INT NOT NULL,
    dateDepot DATE,
    Mail VARCHAR(120),
    PRIMARY KEY(idFichier),
    CONSTRAINT fk_User_Upload
       FOREIGN KEY (Mail)
       REFERENCES Utilisateur (Mail)
       ON DELETE cascade
);

-- -----------------------------------------------------
-- Table Flux
-- -----------------------------------------------------

CREATE TABLE Flux(
    NumFlux NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    Debit INT NOT NULL,
    idFichier NUMBER,
    PRIMARY KEY (NumFlux),
    CONSTRAINT fk_Fichier_flux
        FOREIGN KEY (idFichier)
        REFERENCES Fichier (idFichier)
        ON DELETE cascade
);

-- -----------------------------------------------------
-- Table Codec
-- -----------------------------------------------------

CREATE TABLE Codec(
    Codec VARCHAR(30) PRIMARY KEY
);

-- -----------------------------------------------------
-- Table CodecAudio
-- -----------------------------------------------------

CREATE TABLE CodecAudio(
    Codec VARCHAR(30) PRIMARY KEY,
    CONSTRAINT fk_codec_au
        FOREIGN KEY (Codec)
        REFERENCES Codec (Codec)
        ON DELETE cascade
);

-- -----------------------------------------------------
-- Table CodecTexte
-- -----------------------------------------------------

CREATE TABLE CodecTexte(
    Codec VARCHAR(30) PRIMARY KEY,
    CONSTRAINT fk_codec_txt
        FOREIGN KEY (Codec)
        REFERENCES Codec (Codec)
        ON DELETE cascade
);

-- -----------------------------------------------------
-- Table CodecVideo
-- -----------------------------------------------------


CREATE TABLE CodecVideo(
    Codec VARCHAR(30) PRIMARY KEY,
    CONSTRAINT fk_codec_vi
        FOREIGN KEY (Codec)
        REFERENCES Codec (Codec)
        ON DELETE cascade
);

-- -----------------------------------------------------
-- Table Flux_Video
-- -----------------------------------------------------


CREATE TABLE FluxVideo(
    NumFluxVideo NUMBER PRIMARY KEY,
    Codec VARCHAR(30),
    Largeur INT NOT NULL,
    Hauteur INT NOT NULL,
    CONSTRAINT fk_Flux_Video
        FOREIGN KEY (NumFluxVideo)
        REFERENCES Flux (NumFlux)
        ON DELETE cascade,
    CONSTRAINT fk_COdecV
        FOREIGN KEY (Codec)
        REFERENCES CodecVideo (Codec)
);

-- -----------------------------------------------------
-- Table Flux_Audio
-- -----------------------------------------------------


CREATE TABLE FluxAudio(
    NumFluxAudio NUMBER PRIMARY KEY,
    Echantillonage INT,
    LangueA VARCHAR(20),
    Codec VARCHAR(30),
    CONSTRAINT fk_Flux_Audio
        FOREIGN KEY (NumFluxAudio)
        REFERENCES Flux (NumFlux)
        ON DELETE cascade,
    CONSTRAINT fk_LangueAudio
        FOREIGN KEY (LangueA)
        REFERENCES Langue (Nom_langue),
    CONSTRAINT fk_COdecA
        FOREIGN KEY (Codec)
        REFERENCES CodecAudio (Codec)
);

-- -----------------------------------------------------
-- Table Flux_Texte
-- -----------------------------------------------------


CREATE TABLE FluxTexte(
    NumFluxTexte NUMBER PRIMARY KEY,
    LangueTxt VARCHAR(20),
    Codec VARCHAR(30),
    CONSTRAINT fk_Flux_Texte
        FOREIGN KEY (NumFluxTexte)
        REFERENCES Flux (NumFlux)
        ON DELETE cascade,
    CONSTRAINT fk_LangueAudio2
        FOREIGN KEY (LangueTxt)
        REFERENCES Langue (Nom_langue),
    CONSTRAINT fk_COdecT
        FOREIGN KEY (Codec)
        REFERENCES CodecTexte (Codec)
);

-- -----------------------------------------------------
-- Table Artiste
-- -----------------------------------------------------


CREATE TABLE Artiste(
    IdArtiste NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    Nom VARCHAR(25),
    URL_photo VARCHAR(120),
    Specialite VARCHAR(20),
    PRIMARY KEY(IdArtiste)
);

-- -----------------------------------------------------
-- Table Biographie
-- -----------------------------------------------------

CREATE TABLE Biographie(
  IdArtiste NUMBER,
  contenueBio VARCHAR(255),
  PRIMARY KEY (IdArtiste),
  CONSTRAINT fk_biographie
      FOREIGN KEY (IdArtiste)
      REFERENCES Artiste(IdArtiste)
);


-- -----------------------------------------------------
-- Table Date de Naissance
-- -----------------------------------------------------

CREATE TABLE DateNaissance(
  DateDeNaissance DATE PRIMARY KEY
);

-- -----------------------------------------------------
-- Table aPourDateDeNaissance
-- -----------------------------------------------------


CREATE TABLE aPourDateDeNaissance(
  IdArtiste NUMBER PRIMARY KEY NOT NULL,
  DateDeNaissance DATE,
  CONSTRAINT fk_naissance
      FOREIGN KEY (IdArtiste) REFERENCES Artiste(IdArtiste),
      FOREIGN KEY (DateDeNaissance) REFERENCES DateNaissance(DateDeNaissance)
);

-- -----------------------------------------------------
-- Table Album
-- -----------------------------------------------------

CREATE TABLE Album(
  IdAlbum NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
  Titre VARCHAR(50),
  IdArtiste INT,
  dateSortie DATE NOT NULL,
  urlAlbum VARCHAR(200) NOT NULL,
  PRIMARY KEY(IDALbum),
  CONSTRAINT fk_artistealbum
    FOREIGN KEY (IdArtiste)
    REFERENCES Artiste(IdArtiste)
);

-- -----------------------------------------------------
-- Table PisteMusicale
-- -----------------------------------------------------

CREATE TABLE PisteMusicale(
  IdAlbum NUMBER,
  NumPiste NUMBER,
  Titre VARCHAR(50),
  Duree INT,
  PRIMARY KEY (NumPiste, IdAlbum),
  CONSTRAINT fk_pisteAlbum
    FOREIGN KEY (IdAlbum)
    REFERENCES Album(IdAlbum)
    ON DELETE cascade
);

-- -----------------------------------------------------
-- Table Film
-- -----------------------------------------------------

CREATE TABLE Film(
  Titre VARCHAR(50) NOT NULL,
  anneeSortie NUMBER(4),
  Resume VARCHAR(255),
  ageMin INT,
  URLaffiche VARCHAR(200),
  PRIMARY KEY (Titre, anneeSortie)
);

-- -----------------------------------------------------
-- Table FichierFilm
-- -----------------------------------------------------

CREATE TABLE FichierFilm(
  Titre VARCHAR(50) NOT NULL,
  anneeSortie NUMBER(4),
  idFichier NUMBER,
  PRIMARY KEY (Titre, anneeSortie, idFichier),
  CONSTRAINT fk_fichfilm
     FOREIGN KEY (idFichier) REFERENCES Fichier(idFichier) ON DELETE cascade,
     FOREIGN KEY (Titre, anneeSortie) REFERENCES Film(Titre, anneeSortie) ON DELETE cascade
);

-- -----------------------------------------------------
-- Table FichierPiste
-- -----------------------------------------------------

CREATE TABLE FichierPiste(
  idAlbum NUMBER,
  NumPiste NUMBER,
  idFichier NUMBER,
  PRIMARY KEY (idAlbum, NumPiste, idFichier),
  CONSTRAINT fk_fichpiste
     FOREIGN KEY (idFichier) REFERENCES Fichier(idFichier) ON DELETE cascade,
     FOREIGN KEY (idAlbum, NumPiste) REFERENCES PisteMusicale(idAlbum, NumPiste) ON DELETE cascade
);

-- -----------------------------------------------------
-- Table Client
-- -----------------------------------------------------

CREATE TABLE Client(
  Marque VARCHAR(50) NOT NULL,
  Modele VARCHAR(50) NOT NULL,
  Resume VARCHAR(255),
  largeurMax INT,
  hauteurMax INT, 
  PRIMARY KEY (Marque, Modele)

);

-- -----------------------------------------------------
-- Table ClientFlux
-- -----------------------------------------------------

CREATE TABLE ClientFlux(
  Marque VARCHAR(50) NOT NULL,
  Modele VARCHAR(50) NOT NULL,
  NumFlux NUMBER,
  PRIMARY KEY (Marque, Modele, NumFlux),
  CONSTRAINT fk_Client
    FOREIGN KEY (Marque, Modele) REFERENCES Client(Marque, Modele),
    FOREIGN KEY (NumFlux) REFERENCES Flux(NumFlux)

);

-- -----------------------------------------------------
-- Table CategorieFilm
-- -----------------------------------------------------

CREATE TABLE CategorieFilm(
  Categorie VARCHAR(30) PRIMARY KEY
);

-- -----------------------------------------------------
-- Table CategorieMusique
-- -----------------------------------------------------

CREATE TABLE CategorieMusique(
  Categorie VARCHAR(30) PRIMARY KEY
);

-- -----------------------------------------------------
-- Table AssocFilmCat
-- -----------------------------------------------------

CREATE TABLE AssocFilmCat(
  Titre VARCHAR(50) NOT NULL,
  anneeSortie NUMBER(4),
  Categorie VARCHAR(30),
  PRIMARY KEY (Titre, anneeSortie, Categorie)
);

-- -----------------------------------------------------
-- Table AssocAlbumCat
-- -----------------------------------------------------

CREATE TABLE AssocAlbumCat(
  idAlbum NUMBER,
  Categorie VARCHAR(30),
  PRIMARY KEY (idAlbum, Categorie),
  CONSTRAINT fk_CatAA
    FOREIGN KEY (Categorie) REFERENCES CategorieMusique(Categorie),
  CONSTRAINT fk_CatAlbum
    FOREIGN KEY (idAlbum) REFERENCES Album(idAlbum)
    ON DELETE cascade
);

-- -----------------------------------------------------
-- Table AssocPisteCat
-- -----------------------------------------------------

CREATE TABLE AssocPisteCat(
  idAlbum NUMBER,
  NumPiste NUMBER,
  Categorie VARCHAR(30),
  PRIMARY KEY (idAlbum, NumPiste, Categorie)
);


-- -----------------------------------------------------
-- Table aPourRole
-- -----------------------------------------------------

CREATE TABLE aPourRole(
  idArtiste NUMBER,
  Titre VARCHAR(50) NOT NULL,
  anneeSortie NUMBER(4),
  nomDuPersonnage Varchar(30),
  Categorie VARCHAR(50),
  PRIMARY KEY (Titre, anneeSortie, idArtiste)
);



ALTER TABLE AssocFilmCat
  ADD CONSTRAINT AssocFilmCat_fk FOREIGN KEY (Titre, anneeSortie) REFERENCES Film(Titre, anneeSortie);
ALTER TABLE AssocFilmCat
  ADD CONSTRAINT AssocFilmCat2_fk FOREIGN KEY (Categorie) REFERENCES CategorieFilm(Categorie);
ALTER TABLE AssocPisteCat
  ADD CONSTRAINT fk_piste_cour FOREIGN KEY (idAlbum, NumPiste) REFERENCES PisteMusicale(idAlbum, NumPiste) ON DELETE cascade;
ALTER TABLE AssocPisteCat
  ADD CONSTRAINT fk_CatPiste FOREIGN KEY (Categorie) REFERENCES CategorieMusique(Categorie);
ALTER TABLE aPourRole
  ADD CONSTRAINT AssocRole1_fk FOREIGN KEY (Titre, anneeSortie) REFERENCES Film(Titre, anneeSortie);
ALTER TABLE FluxAudio
  ADD CONSTRAINT Echantiollange_fk check (echantillonage in (8, 16, 32));






INSERT INTO LANGUE (Nom_langue) Values ('Albanais');
INSERT INTO LANGUE (Nom_langue) Values ('Allemand');
INSERT INTO LANGUE (Nom_langue) Values ('Amazigh');
INSERT INTO LANGUE (Nom_langue) Values ('﻿Anglais');
INSERT INTO LANGUE (Nom_langue) Values ('Arabe');
INSERT INTO LANGUE (Nom_langue) Values ('Arménien');
INSERT INTO LANGUE (Nom_langue) Values ('Aymara');
INSERT INTO LANGUE (Nom_langue) Values ('Bengali');
INSERT INTO LANGUE (Nom_langue) Values ('Catalan');
INSERT INTO LANGUE (Nom_langue) Values ('Chinois');
INSERT INTO LANGUE (Nom_langue) Values ('Coréen');
INSERT INTO LANGUE (Nom_langue) Values ('Croate');
INSERT INTO LANGUE (Nom_langue) Values ('Danois');
INSERT INTO LANGUE (Nom_langue) Values ('Espagnol');
INSERT INTO LANGUE (Nom_langue) Values ('Français');
INSERT INTO LANGUE (Nom_langue) Values ('Guarani');
INSERT INTO LANGUE (Nom_langue) Values ('Grec');
INSERT INTO LANGUE (Nom_langue) Values ('Hongrois');
INSERT INTO LANGUE (Nom_langue) Values ('Italien');
INSERT INTO LANGUE (Nom_langue) Values ('Japonais');
INSERT INTO LANGUE (Nom_langue) Values ('Kikongo');
INSERT INTO LANGUE (Nom_langue) Values ('Kiswahili');
INSERT INTO LANGUE (Nom_langue) Values ('Lingala');
INSERT INTO LANGUE (Nom_langue) Values ('Malais');
INSERT INTO LANGUE (Nom_langue) Values ('Mongol');
INSERT INTO LANGUE (Nom_langue) Values ('Néerlandais');
INSERT INTO LANGUE (Nom_langue) Values ('Occitan');
INSERT INTO LANGUE (Nom_langue) Values ('Ourdou');
INSERT INTO LANGUE (Nom_langue) Values ('Persan');
INSERT INTO LANGUE (Nom_langue) Values ('Portugais');
INSERT INTO LANGUE (Nom_langue) Values ('Quechua');
INSERT INTO LANGUE (Nom_langue) Values ('Roumain');
INSERT INTO LANGUE (Nom_langue) Values ('Russe');
INSERT INTO LANGUE (Nom_langue) Values ('Samoan');
INSERT INTO LANGUE (Nom_langue) Values ('Serbe');
INSERT INTO LANGUE (Nom_langue) Values ('Sesotho');
INSERT INTO LANGUE (Nom_langue) Values ('Slovaque');
INSERT INTO LANGUE (Nom_langue) Values ('Slovène');
INSERT INTO LANGUE (Nom_langue) Values ('Suédois');
INSERT INTO LANGUE (Nom_langue) Values ('Tamoul');
INSERT INTO LANGUE (Nom_langue) Values ('Turc');

INSERT INTO CategorieFilm (Categorie) Values ('Romance');
INSERT INTO CategorieFilm (Categorie) Values ('Comédie');
INSERT INTO CategorieFilm (Categorie) Values ('Drame');
INSERT INTO CategorieFilm (Categorie) Values ('Action');
INSERT INTO CategorieFilm (Categorie) Values ('Horreur');
INSERT INTO CategorieFilm (Categorie) Values ('Policier');
INSERT INTO CategorieFilm (Categorie) Values ('Histoire');
INSERT INTO CategorieFilm (Categorie) Values ('Amour');
INSERT INTO CategorieFilm (Categorie) Values ('Science-fiction');
INSERT INTO CategorieFilm (Categorie) Values ('Americana');
INSERT INTO CategorieFilm (Categorie) Values ('Art vidéo');
INSERT INTO CategorieFilm (Categorie) Values ('Buddy movie');
INSERT INTO CategorieFilm (Categorie) Values ('Chanbara');
INSERT INTO CategorieFilm (Categorie) Values ('Chronique');
INSERT INTO CategorieFilm (Categorie) Values ('Cinéma amateur');
INSERT INTO CategorieFilm (Categorie) Values ('Cinéma demontagne');
INSERT INTO CategorieFilm (Categorie) Values ('Cinéma expérimental');
INSERT INTO CategorieFilm (Categorie) Values ('Cinéma abstrait');
INSERT INTO CategorieFilm (Categorie) Values ('Cinéma structurel');
INSERT INTO CategorieFilm (Categorie) Values ('Cinéma underground');
INSERT INTO CategorieFilm (Categorie) Values ('Comédie dramatique');
INSERT INTO CategorieFilm (Categorie) Values ('Comédie policière');
INSERT INTO CategorieFilm (Categorie) Values ('Comédie romantique');
INSERT INTO CategorieFilm (Categorie) Values ('Parodie');
INSERT INTO CategorieFilm (Categorie) Values ('Screwba llcomedy');
INSERT INTO CategorieFilm (Categorie) Values ('Documentaire');
INSERT INTO CategorieFilm (Categorie) Values ('Cinéma ethnographique');
INSERT INTO CategorieFilm (Categorie) Values ('Cinéma observation');
INSERT INTO CategorieFilm (Categorie) Values ('Cinéma vérité');
INSERT INTO CategorieFilm (Categorie) Values ('Ciném adirect');
INSERT INTO CategorieFilm (Categorie) Values ('Docufiction');
INSERT INTO CategorieFilm (Categorie) Values ('Ethnofiction');
INSERT INTO CategorieFilm (Categorie) Values ('Essai cinématographique');
INSERT INTO CategorieFilm (Categorie) Values ('Film archives');
INSERT INTO CategorieFilm (Categorie) Values ('Journal filmé');
INSERT INTO CategorieFilm (Categorie) Values ('Portrait');
INSERT INTO CategorieFilm (Categorie) Values ('Cinéma surréaliste');
INSERT INTO CategorieFilm (Categorie) Values ('Mélodrame');
INSERT INTO CategorieFilm (Categorie) Values ('Docudrama');
INSERT INTO CategorieFilm (Categorie) Values ('Sketches');
INSERT INTO CategorieFilm (Categorie) Values ('Suspense');
INSERT INTO CategorieFilm (Categorie) Values ('Aventures');
INSERT INTO CategorieFilm (Categorie) Values ('Criminel');
INSERT INTO CategorieFilm (Categorie) Values ('Gangsters');
INSERT INTO CategorieFilm (Categorie) Values ('Film noir');
INSERT INTO CategorieFilm (Categorie) Values ('Thriller');
INSERT INTO CategorieFilm (Categorie) Values ('Cambriolage');
INSERT INTO CategorieFilm (Categorie) Values ('Erotique');
INSERT INTO CategorieFilm (Categorie) Values ('Espionnage');
INSERT INTO CategorieFilm (Categorie) Values ('Fantastique');
INSERT INTO CategorieFilm (Categorie) Values ('Vampires');
INSERT INTO CategorieFilm (Categorie) Values ('Zombies');
INSERT INTO CategorieFilm (Categorie) Values ('Guerre');
INSERT INTO CategorieFilm (Categorie) Values ('Biographie');
INSERT INTO CategorieFilm (Categorie) Values ('Autobiographie');
INSERT INTO CategorieFilm (Categorie) Values ('Film publicitaire');
INSERT INTO CategorieFilm (Categorie) Values ('Super-héros');
INSERT INTO CategorieFilm (Categorie) Values ('Film musical');
INSERT INTO CategorieFilm (Categorie) Values ('Film opéra');
INSERT INTO CategorieFilm (Categorie) Values ('Teen movie');
INSERT INTO CategorieFilm (Categorie) Values ('Sérial');
INSERT INTO CategorieFilm (Categorie) Values ('Troma');
INSERT INTO CategorieFilm (Categorie) Values ('Western');

INSERT INTO CategorieMusique (Categorie) Values ('Afro');
INSERT INTO CategorieMusique (Categorie) Values ('Blues');
INSERT INTO CategorieMusique (Categorie) Values ('Country');
INSERT INTO CategorieMusique (Categorie) Values ('Dancehall');
INSERT INTO CategorieMusique (Categorie) Values ('Disco');
INSERT INTO CategorieMusique (Categorie) Values ('Electro');
INSERT INTO CategorieMusique (Categorie) Values ('Fado');
INSERT INTO CategorieMusique (Categorie) Values ('Flamenco');
INSERT INTO CategorieMusique (Categorie) Values ('Funk');
INSERT INTO CategorieMusique (Categorie) Values ('Gospel');
INSERT INTO CategorieMusique (Categorie) Values ('Hard rock');
INSERT INTO CategorieMusique (Categorie) Values ('Jazz');
INSERT INTO CategorieMusique (Categorie) Values ('K-indie');
INSERT INTO CategorieMusique (Categorie) Values ('K-pop');
INSERT INTO CategorieMusique (Categorie) Values ('K-rap');
INSERT INTO CategorieMusique (Categorie) Values ('K-rock');
INSERT INTO CategorieMusique (Categorie) Values ('Kompa');
INSERT INTO CategorieMusique (Categorie) Values ('Makossa');
INSERT INTO CategorieMusique (Categorie) Values ('Metal');
INSERT INTO CategorieMusique (Categorie) Values ('Musique Indi');
INSERT INTO CategorieMusique (Categorie) Values ('Musique latine');
INSERT INTO CategorieMusique (Categorie) Values ('New wave');
INSERT INTO CategorieMusique (Categorie) Values ('Pop');
INSERT INTO CategorieMusique (Categorie) Values ('Punk');
INSERT INTO CategorieMusique (Categorie) Values ('rai');
INSERT INTO CategorieMusique (Categorie) Values ('Rap');
INSERT INTO CategorieMusique (Categorie) Values ('Raï');
INSERT INTO CategorieMusique (Categorie) Values ('Reggae');
INSERT INTO CategorieMusique (Categorie) Values ('Rock n roll');
INSERT INTO CategorieMusique (Categorie) Values ('RnB');
INSERT INTO CategorieMusique (Categorie) Values ('Salsa');
INSERT INTO CategorieMusique (Categorie) Values ('Ska');
INSERT INTO CategorieMusique (Categorie) Values ('Soul');
INSERT INTO CategorieMusique (Categorie) Values ('Zouk');

INSERT INTO Codec (codec) Values ('8SVX ');
INSERT INTO CodecAudio (codec) Values ('8SVX ');
INSERT INTO Codec (codec) Values ('AAC');
INSERT INTO CodecAudio (codec) Values ('AAC');
INSERT INTO Codec (codec) Values ('AC-3');
INSERT INTO CodecAudio (codec) Values ('AC-3');
INSERT INTO Codec (codec) Values ('AMR');
INSERT INTO CodecAudio (codec) Values ('AMR');
INSERT INTO Codec (codec) Values ('AMR-WB');
INSERT INTO CodecAudio (codec) Values ('AMR-WB');
INSERT INTO Codec (codec) Values ('Apple Lossless');
INSERT INTO CodecAudio (codec) Values ('Apple Lossless');
INSERT INTO Codec (codec) Values ('Cook Codec');
INSERT INTO CodecAudio (codec) Values ('Cook Codec');
INSERT INTO Codec (codec) Values ('DTS');
INSERT INTO CodecAudio (codec) Values ('DTS');
INSERT INTO Codec (codec) Values ('EA ADPCM');
INSERT INTO CodecAudio (codec) Values ('EA ADPCM');
INSERT INTO Codec (codec) Values ('E-AC-3');
INSERT INTO CodecAudio (codec) Values ('E-AC-3');
INSERT INTO Codec (codec) Values ('EVRC');
INSERT INTO CodecAudio (codec) Values ('EVRC');
INSERT INTO Codec (codec) Values ('FLAC');
INSERT INTO CodecAudio (codec) Values ('FLAC');
INSERT INTO Codec (codec) Values ('G.711');
INSERT INTO CodecAudio (codec) Values ('G.711');
INSERT INTO Codec (codec) Values ('G.722');
INSERT INTO CodecAudio (codec) Values ('G.722');
INSERT INTO Codec (codec) Values ('G.723.1');
INSERT INTO CodecAudio (codec) Values ('G.723.1');
INSERT INTO Codec (codec) Values ('G.726');
INSERT INTO CodecAudio (codec) Values ('G.726');
INSERT INTO Codec (codec) Values ('G.729');
INSERT INTO CodecAudio (codec) Values ('G.729');
INSERT INTO Codec (codec) Values ('GSM 06.120');
INSERT INTO CodecAudio (codec) Values ('GSM 06.120');
INSERT INTO Codec (codec) Values ('Intel Music Coder ');
INSERT INTO CodecAudio (codec) Values ('Intel Music Coder ');
INSERT INTO Codec (codec) Values ('Meridian Lossless');
INSERT INTO CodecAudio (codec) Values ('Meridian Lossless');
INSERT INTO Codec (codec) Values ('Dolby TrueHD');
INSERT INTO CodecAudio (codec) Values ('Dolby TrueHD');
INSERT INTO Codec (codec) Values ('Monkeys Audio');
INSERT INTO CodecAudio (codec) Values ('Monkeys Audio');
INSERT INTO Codec (codec) Values ('MP1');
INSERT INTO CodecAudio (codec) Values ('MP1');
INSERT INTO Codec (codec) Values ('MP2');
INSERT INTO CodecAudio (codec) Values ('MP2');
INSERT INTO Codec (codec) Values ('MP3');
INSERT INTO CodecAudio (codec) Values ('MP3');
INSERT INTO Codec (codec) Values ('Nellymoser Asao');
INSERT INTO CodecAudio (codec) Values ('Nellymoser Asao');
INSERT INTO Codec (codec) Values ('Opus');
INSERT INTO CodecAudio (codec) Values ('Opus');
INSERT INTO Codec (codec) Values ('QCELP');
INSERT INTO CodecAudio (codec) Values ('QCELP');
INSERT INTO Codec (codec) Values ('QDM2');
INSERT INTO CodecAudio (codec) Values ('QDM2');
INSERT INTO Codec (codec) Values ('RealAudio 1.0');
INSERT INTO CodecAudio (codec) Values ('RealAudio 1.0');
INSERT INTO Codec (codec) Values ('RealAudio 2.0');
INSERT INTO CodecAudio (codec) Values ('RealAudio 2.0');
INSERT INTO Codec (codec) Values ('Shorten');
INSERT INTO CodecAudio (codec) Values ('Shorten');
INSERT INTO Codec (codec) Values ('Truespeech');
INSERT INTO CodecAudio (codec) Values ('Truespeech');
INSERT INTO Codec (codec) Values ('TTA');
INSERT INTO CodecAudio (codec) Values ('TTA');
INSERT INTO Codec (codec) Values ('TwinVQ');
INSERT INTO CodecAudio (codec) Values ('TwinVQ');
INSERT INTO Codec (codec) Values ('Vorbis');
INSERT INTO CodecAudio (codec) Values ('Vorbis');
INSERT INTO Codec (codec) Values ('WavPack');
INSERT INTO CodecAudio (codec) Values ('WavPack');
INSERT INTO Codec (codec) Values ('Windows Media Audio 1 and 2');
INSERT INTO CodecAudio (codec) Values ('Windows Media Audio 1 and 2');
INSERT INTO Codec (codec) Values ('Windows Media Audio 9 Lossless');
INSERT INTO CodecAudio (codec) Values ('Windows Media Audio 9 Lossless');
INSERT INTO Codec (codec) Values ('Windows Media Audio 9 Pro');
INSERT INTO CodecAudio (codec) Values ('Windows Media Audio 9 Pro');
INSERT INTO Codec (codec) Values ('Windows Media Audio Voice');
INSERT INTO CodecAudio (codec) Values ('Windows Media Audio Voice');


INSERT INTO Codec (codec) Values ('Asus');
INSERT INTO CodecVideo (codec) Values ('Asus');
INSERT INTO Codec (codec) Values ('AVS');
INSERT INTO CodecVideo (codec) Values ('AVS');
INSERT INTO Codec (codec) Values ('AV1');
INSERT INTO CodecVideo (codec) Values ('AV1');
INSERT INTO Codec (codec) Values ('CamStudio');
INSERT INTO CodecVideo (codec) Values ('CamStudio');
INSERT INTO Codec (codec) Values ('CineForm');
INSERT INTO CodecVideo (codec) Values ('CineForm');
INSERT INTO Codec (codec) Values ('Cinepak');
INSERT INTO CodecVideo (codec) Values ('Cinepak');
INSERT INTO Codec (codec) Values ('Creative YUV');
INSERT INTO CodecVideo (codec) Values ('Creative YUV');
INSERT INTO Codec (codec) Values ('Dirac');
INSERT INTO CodecVideo (codec) Values ('Dirac');
INSERT INTO Codec (codec) Values ('DNxHD');
INSERT INTO CodecVideo (codec) Values ('DNxHD');
INSERT INTO Codec (codec) Values ('FFV1');
INSERT INTO CodecVideo (codec) Values ('FFV1');
INSERT INTO Codec (codec) Values ('Flash Screen Video');
INSERT INTO CodecVideo (codec) Values ('Flash Screen Video');
INSERT INTO Codec (codec) Values ('H.261');
INSERT INTO CodecVideo (codec) Values ('H.261');
INSERT INTO Codec (codec) Values ('H.262/MPEG-2');
INSERT INTO CodecVideo (codec) Values ('H.262/MPEG-2');
INSERT INTO Codec (codec) Values ('H.263');
INSERT INTO CodecVideo (codec) Values ('H.263');
INSERT INTO Codec (codec) Values ('H.264/MPEG-4 AVC');
INSERT INTO CodecVideo (codec) Values ('H.264/MPEG-4 AVC');
INSERT INTO Codec (codec) Values ('HEVC');
INSERT INTO CodecVideo (codec) Values ('HEVC');
INSERT INTO Codec (codec) Values ('Huffyuv');
INSERT INTO CodecVideo (codec) Values ('Huffyuv');
INSERT INTO Codec (codec) Values ('id Software RoQ');
INSERT INTO CodecVideo (codec) Values ('id Software RoQ');
INSERT INTO Codec (codec) Values ('Indeo');
INSERT INTO CodecVideo (codec) Values ('Indeo');
INSERT INTO Codec (codec) Values ('Lagarith');
INSERT INTO CodecVideo (codec) Values ('Lagarith');
INSERT INTO Codec (codec) Values ('LOCO');
INSERT INTO CodecVideo (codec) Values ('LOCO');
INSERT INTO Codec (codec) Values ('Mimic');
INSERT INTO CodecVideo (codec) Values ('Mimic');
INSERT INTO Codec (codec) Values ('MJPEG');
INSERT INTO CodecVideo (codec) Values ('MJPEG');
INSERT INTO Codec (codec) Values ('MPEG-1');
INSERT INTO CodecVideo (codec) Values ('MPEG-1');
INSERT INTO Codec (codec) Values ('MPEG-4');
INSERT INTO CodecVideo (codec) Values ('MPEG-4');
INSERT INTO Codec (codec) Values ('Apple ProRes');
INSERT INTO CodecVideo (codec) Values ('Apple ProRes');
INSERT INTO Codec (codec) Values ('QuickDraw');
INSERT INTO CodecVideo (codec) Values ('QuickDraw');
INSERT INTO Codec (codec) Values ('QuickTime');
INSERT INTO CodecVideo (codec) Values ('QuickTime');
INSERT INTO Codec (codec) Values ('RealVideo');
INSERT INTO CodecVideo (codec) Values ('RealVideo');
INSERT INTO Codec (codec) Values ('SheerVideo');
INSERT INTO CodecVideo (codec) Values ('SheerVideo');
INSERT INTO Codec (codec) Values ('Smacker video');
INSERT INTO CodecVideo (codec) Values ('Smacker video');
INSERT INTO Codec (codec) Values ('Snow');
INSERT INTO CodecVideo (codec) Values ('Snow');
INSERT INTO Codec (codec) Values ('FLV1');
INSERT INTO CodecVideo (codec) Values ('FLV1');
INSERT INTO Codec (codec) Values ('SVQ1');
INSERT INTO CodecVideo (codec) Values ('SVQ1');
INSERT INTO Codec (codec) Values ('SVQ3');
INSERT INTO CodecVideo (codec) Values ('SVQ3');
INSERT INTO Codec (codec) Values ('Theora');
INSERT INTO CodecVideo (codec) Values ('Theora');
INSERT INTO Codec (codec) Values ('TrueMotion v1 and v2');
INSERT INTO CodecVideo (codec) Values ('TrueMotion v1 and v2');
INSERT INTO Codec (codec) Values ('Sierra VMD Video');
INSERT INTO CodecVideo (codec) Values ('Sierra VMD Video');
INSERT INTO Codec (codec) Values ('VMware VMnc');
INSERT INTO CodecVideo (codec) Values ('VMware VMnc');
INSERT INTO Codec (codec) Values ('VP3');
INSERT INTO CodecVideo (codec) Values ('VP3');
INSERT INTO Codec (codec) Values ('VP5');
INSERT INTO CodecVideo (codec) Values ('VP5');
INSERT INTO Codec (codec) Values ('VP6');
INSERT INTO CodecVideo (codec) Values ('VP6');
INSERT INTO Codec (codec) Values ('VP7');
INSERT INTO CodecVideo (codec) Values ('VP7');
INSERT INTO Codec (codec) Values ('VP8');
INSERT INTO CodecVideo (codec) Values ('VP8');
INSERT INTO Codec (codec) Values ('VP9');
INSERT INTO CodecVideo (codec) Values ('VP9');
INSERT INTO Codec (codec) Values ('VQA');
INSERT INTO CodecVideo (codec) Values ('VQA');
INSERT INTO Codec (codec) Values ('WMV v7 and v8');
INSERT INTO CodecVideo (codec) Values ('WMV v7 and v8');
INSERT INTO Codec (codec) Values ('WMV version 9');
INSERT INTO CodecVideo (codec) Values ('WMV version 9');
INSERT INTO Codec (codec) Values ('WMV Image');
INSERT INTO CodecVideo (codec) Values ('WMV Image');
INSERT INTO Codec (codec) Values ('WMV Screen 1 and 2');
INSERT INTO CodecVideo (codec) Values ('WMV Screen 1 and 2');
INSERT INTO Codec (codec) Values ('Wing Commander');
INSERT INTO CodecVideo (codec) Values ('Wing Commander');


INSERT INTO Codec (codec) Values ('SRT');
INSERT INTO CodecTexte (codec) Values ('SRT');
INSERT INTO Codec (codec) Values ('SSA');
INSERT INTO CodecTexte (codec) Values ('SSA');
INSERT INTO Codec (codec) Values ('ASS');
INSERT INTO CodecTexte (codec) Values ('ASS');
INSERT INTO Codec (codec) Values ('SAMI');
INSERT INTO CodecTexte (codec) Values ('SAMI');
INSERT INTO Codec (codec) Values ('AQTitle');
INSERT INTO CodecTexte (codec) Values ('AQTitle');
INSERT INTO Codec (codec) Values ('DKS');
INSERT INTO CodecTexte (codec) Values ('DKS');
INSERT INTO Codec (codec) Values ('Kate');
INSERT INTO CodecTexte (codec) Values ('Kate');
