set schema PROCESSED;

drop table TESTRESULT;
drop table JOBBUILD;
drop table JOB;
drop table TEST;
drop table UPSTREAMBUILD;
drop table UPSTREAMJOB;

create table UpstreamJob (
    id identity primary key,
    name varchar(128) not null,
    suite varchar(128) not null,
    url varchar(256) not null
);

create table UpstreamBuild (
    id identity primary key,
    upstreamJob int not null references UpstreamJob (id),
    buildNumber bigint not null,
    build varchar(64) not null,
    url varchar(256) not null,
    status varchar(128) not null,
    buildTimestamp bigint not null
);

create table Job (
    id identity primary key,
    upstream int not null references UpstreamJob (id),
    name varchar(128) not null,
    sddc varchar(128) not null,
    url varchar(256) not null
);

create table JobBuild (
    id identity primary key,
    job int not null references Job (id),
    upstreamBuild int not null references UpstreamBuild (id),
    buildNumber bigint not null,
    url varchar(256) not null,
    commitId varchar(256) not null,
    status varchar(128) not null,
    failedCount int not null,
    skippedCount int not null,
    buildTimestamp bigint not null
);

create table Test (
    id identity primary key,
    upstream int not null references UpstreamJob (id),
    name varchar(128) not null,
    dataProviderIndex int not null,
    className varchar(256) not null,
    packagePath varchar(256) not null,
    parameters varchar not null
);

create table TestResult (
    id identity primary key,
    test int not null references Test (id),
    build int not null references JobBuild (id),
    status varchar(128) not null,
    exception varchar,
    duration double not null,
    startedAt bigint not null
);
