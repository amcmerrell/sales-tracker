--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: customers; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE customers (
    id integer NOT NULL,
    name character varying,
    email character varying,
    address character varying
);


ALTER TABLE customers OWNER TO "Guest";

--
-- Name: customers_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE customers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE customers_id_seq OWNER TO "Guest";

--
-- Name: customers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE customers_id_seq OWNED BY customers.id;


--
-- Name: products; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE products (
    id integer NOT NULL,
    name character varying,
    description character varying,
    price integer,
    inventory integer,
    type character varying
);


ALTER TABLE products OWNER TO "Guest";

--
-- Name: products_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE products_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE products_id_seq OWNER TO "Guest";

--
-- Name: products_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE products_id_seq OWNED BY products.id;


--
-- Name: transactions; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE transactions (
    id integer NOT NULL,
    date timestamp without time zone,
    saleprice integer,
    productid integer,
    customerid integer,
    productname character varying
);


ALTER TABLE transactions OWNER TO "Guest";

--
-- Name: transactions_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE transactions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE transactions_id_seq OWNER TO "Guest";

--
-- Name: transactions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE transactions_id_seq OWNED BY transactions.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY customers ALTER COLUMN id SET DEFAULT nextval('customers_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY products ALTER COLUMN id SET DEFAULT nextval('products_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY transactions ALTER COLUMN id SET DEFAULT nextval('transactions_id_seq'::regclass);


--
-- Data for Name: customers; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY customers (id, name, email, address) FROM stdin;
1	Andrew	andrew@email.com	123123 street pl.
2	Yusuf	blah@blah.com	12345 blah street
\.


--
-- Name: customers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('customers_id_seq', 2, true);


--
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY products (id, name, description, price, inventory, type) FROM stdin;
\.


--
-- Name: products_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('products_id_seq', 7, true);


--
-- Data for Name: transactions; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY transactions (id, date, saleprice, productid, customerid, productname) FROM stdin;
49	2016-09-29 14:53:38.143262	12	7	1	Hammer
\.


--
-- Name: transactions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('transactions_id_seq', 49, true);


--
-- Name: customers_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY customers
    ADD CONSTRAINT customers_pkey PRIMARY KEY (id);


--
-- Name: products_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


--
-- Name: transactions_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY transactions
    ADD CONSTRAINT transactions_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: epicodus
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM epicodus;
GRANT ALL ON SCHEMA public TO epicodus;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

