package com.example.osio3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public static PlantDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        database = Room.databaseBuilder(
                getApplicationContext(),
                PlantDatabase.class,
                "database_name").allowMainThreadQueries().build();
        PlantDao plantDao = database.plantDao();
        Plant[] allPlants = plantDao.getAllPlants();
        if(allPlants.length == 0) InitiatePlants(plantDao);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void InitiatePlants(PlantDao dao ){

        Plant plant1 = new Plant();

        plant1.platId = 1;
        plant1.name = "Kultaköynnös";
        plant1.mainText = "Nopeakasvuinen liaanimainen köynnös, joka sopii myös amppeliin. Versot voivat kasvaa jopa 20 m pitkiksi. Kiipeää ilmajuurien avulla. \n" +
                "Viihtyy valossa ja varjossa. Saatavana on useita lajikkeita, mitä vaaleammat lehdet kasvilla on sitä valoisampi kasvupaikka." +
                " Ei paahteeseen! Varjossa lehdet saattavat muuttua kokonaan vihreiksi.\n" +
                "Kastellaan säännöllisesti, kohtalaisen runsaasti. Kastelukertojen välillä multa saa kuivahtaa. Kultaköynnös ei pidä jatkuvasta märkyydestä.";
        plant1.imageId = getResources().getIdentifier("kultakoynnos", "drawable", getPackageName());

        Plant plant2 = new Plant();
        plant2.platId = 3;
        plant2.name = "Anopinkieli";
        plant2.mainText = "Helppohoitoinen ja viihtyy hyvin myös huoneilmassa.\n"+
        "Polivarjossa lehdet kasvavat kiiltävän vihreiksi ja pystyiksi, kovin valoisassa lehdet kaartuvat ulospäin ja jäävät lyhyemmiksi\n" +
        "Kohtalainen kastelu keväästä syksyyn ja talvella niukempi. Kastele multa perusteellisesti ja anna kuivahtaa kasteluiden välillä.";
        plant2.imageId = getResources().getIdentifier("anopinkieli", "drawable", getPackageName());

        Plant plant3 = new Plant();
        plant3.platId = 4;
        plant3.name = "Myrtti";
        plant3.mainText = "Välimeren alueelta kotoisin oleva pensasmainen huonekasvi.\n"+
                "Viihtyy valoisassa ja puolivarjossa. Joidenkin lähteiden mukaan jopa pohjoisikkunalla. Kesällä ulkona.\n" +
                "Tasainen kosteus ympäri vuoden, ei saa päästä kuivahtamaan. Sumutusta.";
        plant3.imageId = getResources().getIdentifier("myrtti", "drawable", getPackageName());

        dao.insertAll(plant1, plant2, plant3);
    }
}