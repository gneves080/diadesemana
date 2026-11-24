import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static void main(String[] args) {
        // 1. Evento com a data e hora atuais
        LocalDateTime agora = LocalDateTime.now();
        Evento evento = new Evento("Reunião de planejamento", agora, paraDiaDaSemana(agora.getDayOfWeek()));

        System.out.println("=== Evento original ===");
        evento.exibirEvento();

        // 2. Adiciona 5 dias com plusDays()
        LocalDateTime novaDataHora = evento.getDataHora().plusDays(5);
        evento.setDataHora(novaDataHora);

        System.out.println();
        System.out.println("=== Após plusDays(5) ===");
        System.out.println("Nova data e hora: " + novaDataHora.format(FORMATO));

        // 3. Conversão de fusos com ZoneId
        // LocalDateTime não tem fuso: primeiro dizemos em qual fuso ele foi criado
        // (o do sistema), e depois convertemos para o mesmo instante nos outros fusos.
        ZonedDateTime origem = novaDataHora.atZone(ZoneId.systemDefault());
        ZonedDateTime saoPaulo = origem.withZoneSameInstant(ZoneId.of("America/Sao_Paulo"));
        ZonedDateTime gmt = origem.withZoneSameInstant(ZoneId.of("GMT"));

        System.out.println();
        System.out.println("=== Conversão de fuso horário ===");
        System.out.println("Fuso de origem (" + origem.getZone() + "): " + origem.format(FORMATO));
        System.out.println("São Paulo (BRT): " + saoPaulo.format(FORMATO) + " " + saoPaulo.getOffset());
        System.out.println("GMT: " + gmt.format(FORMATO) + " " + gmt.getOffset());
    }

    private static DiaDaSemana paraDiaDaSemana(DayOfWeek dia) {
        switch (dia) {
            case MONDAY:    return DiaDaSemana.SEGUNDA;
            case TUESDAY:   return DiaDaSemana.TERÇA;
            case WEDNESDAY: return DiaDaSemana.QUARTA;
            case THURSDAY:  return DiaDaSemana.QUINTA;
            case FRIDAY:    return DiaDaSemana.SEXTA;
            case SATURDAY:  return DiaDaSemana.SÁBADO;
            default:        return DiaDaSemana.DOMINGO;
        }
    }
}