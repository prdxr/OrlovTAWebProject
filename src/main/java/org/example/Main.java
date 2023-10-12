package org.example;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {

            Retrofit client = new Retrofit
                    .Builder()
                    .baseUrl("https://www.cbr.ru")
                    .addConverterFactory(JacksonConverterFactory.create(new XmlMapper()))
                    .build();


            StonksService stonksService = client.create(StonksService.class);
//



            Response<DailyCurs> response = stonksService
                    .getDailyCurs(
                            LocalDate.of(2010, 10, 23)
                                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    ).execute();



            DailyCurs dailyCurs = response.body();
            Optional<Valute> maxValute = dailyCurs.getValutes().stream()
                    .filter(valute -> !valute.getName().equals("СДР (специальные права заимствования)"))
                    .max(Comparator.comparingDouble(Valute::getValue));

            DatabaseService databaseService = new DatabaseServiceImpl();
            if(maxValute.isPresent()) {
                System.out.println(maxValute.get());
                Valute mv = maxValute.get();
                databaseService.saveMaxValuteOfDate("OrlovTA", mv, LocalDate.of(2004,3,18));
            }

            Valute valute = databaseService.getValuteOfDate(LocalDate.of(2004,3,18));

            System.out.println(valute);

        }
    }
