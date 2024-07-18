import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class MyTelegramBot extends TelegramLongPollingBot {
    private static final String BOT_NAME = "NASA_photo_Of_The_Day_Bot";
    private static final String BOT_TOKEN = "6622612575:AAHZijEvxTdl8FfbQkgJCAfUvPeKZZPQiTc";
    private static final String URL = "https://api.nasa.gov/planetary/apod?api_key=IHi02uPaLRV0cNWXFKe3Vbngkr1FFHziTSIUONTE";


    public MyTelegramBot() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }


    @Override
    public void onUpdateReceived(Update update)  {
        if (update.hasMessage() && update.getMessage().hasText()) {
            switch (update.getMessage().getText()) {
                case "/help":
                    sendMessage("Привет, я бот NASA, напиши /start или /give, чтобы получить" +
                            "картинку дня", update.getMessage().getChatId());
                    break;
                case "/start":
                case "/give":
                    String url;
                    try {
                        url = Utils.getURL(URL);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    sendMessage(url, update.getMessage().getChatId());
                    break;
                default:
                    sendMessage("Я не понимаю", update.getMessage().getChatId());
            }


        }
    }
    private void sendMessage(String text, long chatId) {
        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }



    @Override
    public String getBotUsername() {
        // TODO
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        // TODO
        return BOT_TOKEN;
    }
}