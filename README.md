<h1>MineSweeperApp Projektterv</h1>
<h3>Projekt leírás:</h3>
<p>Aknakereső játék, SQL adatbázissal</p>
<p>A felhasználó megadja az adatait (név, jelszó), ez alapján bekerül az adatbázisba, elindul a játék,
és nyerés esetén a teljesítéshez szükséges idő bekerül a felhasználó adataihoz,
melyek alapján Leaderboard készül.</p>
<h3>Résztvetők:</h3>
<p>Máthé-Kiss Gábor - A7VEJ4</p>
<p>Győri Bence Zsolt - C01CLY</p>
<h3>Projekt vezető:</h3>
<p>Győri Bence Zsolt - C01CLY</p>
<h1>Adatbázisterv</h1>
<p>ID - int - not null</p>
<p>Username - varchar(max) - not null</p>
<p>Password - varchar(max) - not null</p>
<p>TimeInSeconds - int - nullable</p>