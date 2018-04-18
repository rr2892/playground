import java.util.*;
import java.io.*;

class SubComparator implements Comparator<Submission> {
  public int compare (Submission s1, Submission s2) {

    for (int k = 0; k < 5; k++) {
      if (s1.date[k] != s2.date[k]) {
        return s1.date[k] - s2.date[k];
      }
    }

    return 0;
  }
}

class GradeComparator implements Comparator<Grade> {
  public int compare (Grade g1, Grade g2) {
    if (g2.finalGrade > g1.finalGrade) return 1;
    if (g1.finalGrade > g2.finalGrade) return -1;
    return g1.name.compareTo(g2.name);
  }
}

class Grade {
  String name;
  double finalGrade;

  Grade (String name, double finalGrade) {
    this.name = name;
    this.finalGrade = finalGrade;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(name);
    sb.append(' ');
    sb.append(finalGrade);
    sb.append("\n");
    return sb.toString();
  }
}

class DateHelper {
  public static int[] getDate(String dateInfo, String timeInfo) {
    String[] dateSplit = dateInfo.split("/");
    int month = Integer.parseInt(dateSplit[0]);
    int day = Integer.parseInt(dateSplit[1]);
    String[] timeSplit = timeInfo.substring(0, 8).split(":");
    String ampm = timeInfo.substring(8);
    int hour = Integer.parseInt(timeSplit[0]);
    if (hour != 12 && ampm.equals("pm")) {
      hour += 12;
    }

    int minute = Integer.parseInt(timeSplit[1]);
    int second = Integer.parseInt(timeSplit[2]);

    int[] date = {month, day, hour, minute, second};
    return date;
  }

  public static int[] getEndDate(int[] startDate) {
    int rollover = 31;

    switch (startDate[0]) {
      case 1: case 3: case 5:
      case 7: case 8: case 10:
      case 12:
          rollover = 31;
          break;
      case 4: case 6:
      case 9: case 11:
          rollover = 30;
          break;
      case 2:
          rollover = 28;
          break;
    }

    int endMonth = startDate[0], endDay = startDate[1] + 7;
    if (endDay > rollover) {
      endDay -= rollover;
      endMonth++;
    }

    int[] endDate = {endMonth, endDay, startDate[2], startDate[3], startDate[4]};
    return endDate;
  }
}

class Submission {
  int[] date;
  String name;
  char id;
  boolean accepted;

  public Submission (int[] date) {
    this.date = date;
  }

  public Submission (String line) {
    StringTokenizer st = new StringTokenizer (line);
    String dateInfo = st.nextToken(), timeInfo = st.nextToken();
    date = DateHelper.getDate(dateInfo, timeInfo);
    name = st.nextToken();
    id = st.nextToken().charAt(0);
    accepted = (st.nextToken().equals("Accepted")) ? true : false;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(date[0]);
    sb.append('/');
    sb.append(date[1]);
    sb.append(' ');
    sb.append(date[2]);
    sb.append(':');
    sb.append(date[3]);
    sb.append(':');
    sb.append(date[4]);
    sb.append(' ');
    sb.append(name);
    sb.append(' ');
    sb.append(id);
    sb.append(' ');
    sb.append(accepted);
    sb.append('\n');
    return sb.toString();
  }

}

public class HomeWorkGrading {

  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    try {
      String line1 = br.readLine();
      StringTokenizer st1 = new StringTokenizer(line1);
      int n = Integer.parseInt(st1.nextToken());
      int k = Integer.parseInt(st1.nextToken());
      Map<Character, Integer> scorekey = new HashMap<>();

      for (int i = 0; i < n; i++) {
        String line = br.readLine();
        StringTokenizer st = new StringTokenizer(line);
        String s = st.nextToken();
        int p = Integer.parseInt(st.nextToken());
        scorekey.put(s.charAt(0), p);
      }

      String dateLine = br.readLine();
      StringTokenizer st2 = new StringTokenizer(dateLine);
      String startDateInfo = st2.nextToken(), startTimeInfo = st2.nextToken();
      int[] startDate = DateHelper.getDate(startDateInfo, startTimeInfo);
      int[] endDate = DateHelper.getEndDate(startDate);
      Submission subStart = new Submission(startDate), subEnd = new Submission(endDate);
      // int[] date = [month, day, hour (24h), minute, second]

      SubComparator comp = new SubComparator();
      HashMap< String, HashMap< Character, PriorityQueue<Submission> > > map = new HashMap<>();
      String line;

      while ((line = br.readLine()) != null) {
        if (line.equals("QUIT")) {
          break;
        }

        Submission sub = new Submission(line);

        if (!map.containsKey(sub.name)) {
          map.put(sub.name, new HashMap<Character, PriorityQueue<Submission> >());
        }

        HashMap<Character, PriorityQueue<Submission> > probs = map.get(sub.name);

        if (!probs.containsKey(sub.id)) {
          probs.put(sub.id, new PriorityQueue<Submission>(comp));
        }

        PriorityQueue<Submission> queue = probs.get(sub.id);
        queue.offer(sub);
      }

      StringBuilder sb = new StringBuilder();

      PriorityQueue<Grade> res = new PriorityQueue<>(new GradeComparator());

      for (String name : map.keySet()) {
        HashMap<Character, PriorityQueue<Submission> > probs = map.get(name);
        double grade = 0;

        for (char id : probs.keySet()) {
          int incorrect = 0;
          PriorityQueue<Submission> queue = probs.get(id);
          boolean solved = false;

          while (!queue.isEmpty() && comp.compare(subStart, queue.peek()) > 0) {
            queue.poll();
          }

          while (!queue.isEmpty() && comp.compare(subEnd, queue.peek()) >= 0) {
            //logic goes here
            Submission sub = queue.poll();
            if (sub.accepted) {
              solved = true;
              break;
            }

            incorrect ++;
          }

          if (solved) {
            incorrect -= k;
            incorrect = (incorrect <= 0) ? 0 : incorrect;
            double score = 1 - (0.1 * incorrect);
            score = (score > 0.3) ? score : 0.3;
            score *= scorekey.get(id);
            grade += score;
          }
        }

        Grade g = new Grade(name, grade);
        res.offer(g);
      }

      while (!res.isEmpty()) {
        sb.append(res.poll().toString());
      }

      System.out.print(sb.toString());

    } catch (IOException e) {
      System.exit(0);
    }
  }
}
