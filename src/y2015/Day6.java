package y2015;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO
 *
 * @author <a href="mailto:d.roden@emetriq.com">David Roden</a>
 */
public class Day6 {

	static String INPUT = "turn off 660,55 through 986,197\n" +
			"turn off 341,304 through 638,850\n" +
			"turn off 199,133 through 461,193\n" +
			"toggle 322,558 through 977,958\n" +
			"toggle 537,781 through 687,941\n" +
			"turn on 226,196 through 599,390\n" +
			"turn on 240,129 through 703,297\n" +
			"turn on 317,329 through 451,798\n" +
			"turn on 957,736 through 977,890\n" +
			"turn on 263,530 through 559,664\n" +
			"turn on 158,270 through 243,802\n" +
			"toggle 223,39 through 454,511\n" +
			"toggle 544,218 through 979,872\n" +
			"turn on 313,306 through 363,621\n" +
			"toggle 173,401 through 496,407\n" +
			"toggle 333,60 through 748,159\n" +
			"turn off 87,577 through 484,608\n" +
			"turn on 809,648 through 826,999\n" +
			"toggle 352,432 through 628,550\n" +
			"turn off 197,408 through 579,569\n" +
			"turn off 1,629 through 802,633\n" +
			"turn off 61,44 through 567,111\n" +
			"toggle 880,25 through 903,973\n" +
			"turn on 347,123 through 864,746\n" +
			"toggle 728,877 through 996,975\n" +
			"turn on 121,895 through 349,906\n" +
			"turn on 888,547 through 931,628\n" +
			"toggle 398,782 through 834,882\n" +
			"turn on 966,850 through 989,953\n" +
			"turn off 891,543 through 914,991\n" +
			"toggle 908,77 through 916,117\n" +
			"turn on 576,900 through 943,934\n" +
			"turn off 580,170 through 963,206\n" +
			"turn on 184,638 through 192,944\n" +
			"toggle 940,147 through 978,730\n" +
			"turn off 854,56 through 965,591\n" +
			"toggle 717,172 through 947,995\n" +
			"toggle 426,987 through 705,998\n" +
			"turn on 987,157 through 992,278\n" +
			"toggle 995,774 through 997,784\n" +
			"turn off 796,96 through 845,182\n" +
			"turn off 451,87 through 711,655\n" +
			"turn off 380,93 through 968,676\n" +
			"turn on 263,468 through 343,534\n" +
			"turn on 917,936 through 928,959\n" +
			"toggle 478,7 through 573,148\n" +
			"turn off 428,339 through 603,624\n" +
			"turn off 400,880 through 914,953\n" +
			"toggle 679,428 through 752,779\n" +
			"turn off 697,981 through 709,986\n" +
			"toggle 482,566 through 505,725\n" +
			"turn off 956,368 through 993,516\n" +
			"toggle 735,823 through 783,883\n" +
			"turn off 48,487 through 892,496\n" +
			"turn off 116,680 through 564,819\n" +
			"turn on 633,865 through 729,930\n" +
			"turn off 314,618 through 571,922\n" +
			"toggle 138,166 through 936,266\n" +
			"turn on 444,732 through 664,960\n" +
			"turn off 109,337 through 972,497\n" +
			"turn off 51,432 through 77,996\n" +
			"turn off 259,297 through 366,744\n" +
			"toggle 801,130 through 917,544\n" +
			"toggle 767,982 through 847,996\n" +
			"turn on 216,507 through 863,885\n" +
			"turn off 61,441 through 465,731\n" +
			"turn on 849,970 through 944,987\n" +
			"toggle 845,76 through 852,951\n" +
			"toggle 732,615 through 851,936\n" +
			"toggle 251,128 through 454,778\n" +
			"turn on 324,429 through 352,539\n" +
			"toggle 52,450 through 932,863\n" +
			"turn off 449,379 through 789,490\n" +
			"turn on 317,319 through 936,449\n" +
			"toggle 887,670 through 957,838\n" +
			"toggle 671,613 through 856,664\n" +
			"turn off 186,648 through 985,991\n" +
			"turn off 471,689 through 731,717\n" +
			"toggle 91,331 through 750,758\n" +
			"toggle 201,73 through 956,524\n" +
			"toggle 82,614 through 520,686\n" +
			"toggle 84,287 through 467,734\n" +
			"turn off 132,367 through 208,838\n" +
			"toggle 558,684 through 663,920\n" +
			"turn on 237,952 through 265,997\n" +
			"turn on 694,713 through 714,754\n" +
			"turn on 632,523 through 862,827\n" +
			"turn on 918,780 through 948,916\n" +
			"turn on 349,586 through 663,976\n" +
			"toggle 231,29 through 257,589\n" +
			"toggle 886,428 through 902,993\n" +
			"turn on 106,353 through 236,374\n" +
			"turn on 734,577 through 759,684\n" +
			"turn off 347,843 through 696,912\n" +
			"turn on 286,699 through 964,883\n" +
			"turn on 605,875 through 960,987\n" +
			"turn off 328,286 through 869,461\n" +
			"turn off 472,569 through 980,848\n" +
			"toggle 673,573 through 702,884\n" +
			"turn off 398,284 through 738,332\n" +
			"turn on 158,50 through 284,411\n" +
			"turn off 390,284 through 585,663\n" +
			"turn on 156,579 through 646,581\n" +
			"turn on 875,493 through 989,980\n" +
			"toggle 486,391 through 924,539\n" +
			"turn on 236,722 through 272,964\n" +
			"toggle 228,282 through 470,581\n" +
			"toggle 584,389 through 750,761\n" +
			"turn off 899,516 through 900,925\n" +
			"turn on 105,229 through 822,846\n" +
			"turn off 253,77 through 371,877\n" +
			"turn on 826,987 through 906,992\n" +
			"turn off 13,152 through 615,931\n" +
			"turn on 835,320 through 942,399\n" +
			"turn on 463,504 through 536,720\n" +
			"toggle 746,942 through 786,998\n" +
			"turn off 867,333 through 965,403\n" +
			"turn on 591,477 through 743,692\n" +
			"turn off 403,437 through 508,908\n" +
			"turn on 26,723 through 368,814\n" +
			"turn on 409,485 through 799,809\n" +
			"turn on 115,630 through 704,705\n" +
			"turn off 228,183 through 317,220\n" +
			"toggle 300,649 through 382,842\n" +
			"turn off 495,365 through 745,562\n" +
			"turn on 698,346 through 744,873\n" +
			"turn on 822,932 through 951,934\n" +
			"toggle 805,30 through 925,421\n" +
			"toggle 441,152 through 653,274\n" +
			"toggle 160,81 through 257,587\n" +
			"turn off 350,781 through 532,917\n" +
			"toggle 40,583 through 348,636\n" +
			"turn on 280,306 through 483,395\n" +
			"toggle 392,936 through 880,955\n" +
			"toggle 496,591 through 851,934\n" +
			"turn off 780,887 through 946,994\n" +
			"turn off 205,735 through 281,863\n" +
			"toggle 100,876 through 937,915\n" +
			"turn on 392,393 through 702,878\n" +
			"turn on 956,374 through 976,636\n" +
			"toggle 478,262 through 894,775\n" +
			"turn off 279,65 through 451,677\n" +
			"turn on 397,541 through 809,847\n" +
			"turn on 444,291 through 451,586\n" +
			"toggle 721,408 through 861,598\n" +
			"turn on 275,365 through 609,382\n" +
			"turn on 736,24 through 839,72\n" +
			"turn off 86,492 through 582,712\n" +
			"turn on 676,676 through 709,703\n" +
			"turn off 105,710 through 374,817\n" +
			"toggle 328,748 through 845,757\n" +
			"toggle 335,79 through 394,326\n" +
			"toggle 193,157 through 633,885\n" +
			"turn on 227,48 through 769,743\n" +
			"toggle 148,333 through 614,568\n" +
			"toggle 22,30 through 436,263\n" +
			"toggle 547,447 through 688,969\n" +
			"toggle 576,621 through 987,740\n" +
			"turn on 711,334 through 799,515\n" +
			"turn on 541,448 through 654,951\n" +
			"toggle 792,199 through 798,990\n" +
			"turn on 89,956 through 609,960\n" +
			"toggle 724,433 through 929,630\n" +
			"toggle 144,895 through 201,916\n" +
			"toggle 226,730 through 632,871\n" +
			"turn off 760,819 through 828,974\n" +
			"toggle 887,180 through 940,310\n" +
			"toggle 222,327 through 805,590\n" +
			"turn off 630,824 through 885,963\n" +
			"turn on 940,740 through 954,946\n" +
			"turn on 193,373 through 779,515\n" +
			"toggle 304,955 through 469,975\n" +
			"turn off 405,480 through 546,960\n" +
			"turn on 662,123 through 690,669\n" +
			"turn off 615,238 through 750,714\n" +
			"turn on 423,220 through 930,353\n" +
			"turn on 329,769 through 358,970\n" +
			"toggle 590,151 through 704,722\n" +
			"turn off 884,539 through 894,671\n" +
			"toggle 449,241 through 984,549\n" +
			"toggle 449,260 through 496,464\n" +
			"turn off 306,448 through 602,924\n" +
			"turn on 286,805 through 555,901\n" +
			"toggle 722,177 through 922,298\n" +
			"toggle 491,554 through 723,753\n" +
			"turn on 80,849 through 174,996\n" +
			"turn off 296,561 through 530,856\n" +
			"toggle 653,10 through 972,284\n" +
			"toggle 529,236 through 672,614\n" +
			"toggle 791,598 through 989,695\n" +
			"turn on 19,45 through 575,757\n" +
			"toggle 111,55 through 880,871\n" +
			"turn off 197,897 through 943,982\n" +
			"turn on 912,336 through 977,605\n" +
			"toggle 101,221 through 537,450\n" +
			"turn on 101,104 through 969,447\n" +
			"toggle 71,527 through 587,717\n" +
			"toggle 336,445 through 593,889\n" +
			"toggle 214,179 through 575,699\n" +
			"turn on 86,313 through 96,674\n" +
			"toggle 566,427 through 906,888\n" +
			"turn off 641,597 through 850,845\n" +
			"turn on 606,524 through 883,704\n" +
			"turn on 835,775 through 867,887\n" +
			"toggle 547,301 through 897,515\n" +
			"toggle 289,930 through 413,979\n" +
			"turn on 361,122 through 457,226\n" +
			"turn on 162,187 through 374,746\n" +
			"turn on 348,461 through 454,675\n" +
			"turn off 966,532 through 985,537\n" +
			"turn on 172,354 through 630,606\n" +
			"turn off 501,880 through 680,993\n" +
			"turn off 8,70 through 566,592\n" +
			"toggle 433,73 through 690,651\n" +
			"toggle 840,798 through 902,971\n" +
			"toggle 822,204 through 893,760\n" +
			"turn off 453,496 through 649,795\n" +
			"turn off 969,549 through 990,942\n" +
			"turn off 789,28 through 930,267\n" +
			"toggle 880,98 through 932,434\n" +
			"toggle 568,674 through 669,753\n" +
			"turn on 686,228 through 903,271\n" +
			"turn on 263,995 through 478,999\n" +
			"toggle 534,675 through 687,955\n" +
			"turn off 342,434 through 592,986\n" +
			"toggle 404,768 through 677,867\n" +
			"toggle 126,723 through 978,987\n" +
			"toggle 749,675 through 978,959\n" +
			"turn off 445,330 through 446,885\n" +
			"turn off 463,205 through 924,815\n" +
			"turn off 417,430 through 915,472\n" +
			"turn on 544,990 through 912,999\n" +
			"turn off 201,255 through 834,789\n" +
			"turn off 261,142 through 537,862\n" +
			"turn off 562,934 through 832,984\n" +
			"turn off 459,978 through 691,980\n" +
			"turn off 73,911 through 971,972\n" +
			"turn on 560,448 through 723,810\n" +
			"turn on 204,630 through 217,854\n" +
			"turn off 91,259 through 611,607\n" +
			"turn on 877,32 through 978,815\n" +
			"turn off 950,438 through 974,746\n" +
			"toggle 426,30 through 609,917\n" +
			"toggle 696,37 through 859,201\n" +
			"toggle 242,417 through 682,572\n" +
			"turn off 388,401 through 979,528\n" +
			"turn off 79,345 through 848,685\n" +
			"turn off 98,91 through 800,434\n" +
			"toggle 650,700 through 972,843\n" +
			"turn off 530,450 through 538,926\n" +
			"turn on 428,559 through 962,909\n" +
			"turn on 78,138 through 92,940\n" +
			"toggle 194,117 through 867,157\n" +
			"toggle 785,355 through 860,617\n" +
			"turn off 379,441 through 935,708\n" +
			"turn off 605,133 through 644,911\n" +
			"toggle 10,963 through 484,975\n" +
			"turn off 359,988 through 525,991\n" +
			"turn off 509,138 through 787,411\n" +
			"toggle 556,467 through 562,773\n" +
			"turn on 119,486 through 246,900\n" +
			"turn on 445,561 through 794,673\n" +
			"turn off 598,681 through 978,921\n" +
			"turn off 974,230 through 995,641\n" +
			"turn off 760,75 through 800,275\n" +
			"toggle 441,215 through 528,680\n" +
			"turn off 701,636 through 928,877\n" +
			"turn on 165,753 through 202,780\n" +
			"toggle 501,412 through 998,516\n" +
			"toggle 161,105 through 657,395\n" +
			"turn on 113,340 through 472,972\n" +
			"toggle 384,994 through 663,999\n" +
			"turn on 969,994 through 983,997\n" +
			"turn on 519,600 through 750,615\n" +
			"turn off 363,899 through 948,935\n" +
			"turn on 271,845 through 454,882\n" +
			"turn off 376,528 through 779,640\n" +
			"toggle 767,98 through 854,853\n" +
			"toggle 107,322 through 378,688\n" +
			"turn off 235,899 through 818,932\n" +
			"turn on 445,611 through 532,705\n" +
			"toggle 629,387 through 814,577\n" +
			"toggle 112,414 through 387,421\n" +
			"toggle 319,184 through 382,203\n" +
			"turn on 627,796 through 973,940\n" +
			"toggle 602,45 through 763,151\n" +
			"turn off 441,375 through 974,545\n" +
			"toggle 871,952 through 989,998\n" +
			"turn on 717,272 through 850,817\n" +
			"toggle 475,711 through 921,882\n" +
			"toggle 66,191 through 757,481\n" +
			"turn off 50,197 through 733,656\n" +
			"toggle 83,575 through 915,728\n" +
			"turn on 777,812 through 837,912\n" +
			"turn on 20,984 through 571,994\n" +
			"turn off 446,432 through 458,648\n" +
			"turn on 715,871 through 722,890\n" +
			"toggle 424,675 through 740,862\n" +
			"toggle 580,592 through 671,900\n" +
			"toggle 296,687 through 906,775";

	static List<String> getInput() {
		return Arrays.asList(INPUT.split("\n"));
	}

	public enum Action {TURN_ON, TURN_OFF, TOGGLE}

	static class Direction {

		public final Action action;
		public final int leftX;
		public final int leftY;
		public final int rightX;
		public final int rightY;

		private Direction(Action action, int leftX, int leftY, int rightX, int rightY) {
			this.action = action;
			this.leftX = leftX;
			this.leftY = leftY;
			this.rightX = rightX;
			this.rightY = rightY;
		}

		public int[][] processLights(int[][] lights) {
			for (int y = leftY; y <= rightY; y++) {
				for (int x = leftX; x <= rightX; x++) {
					if (action == Action.TURN_ON) {
						lights[y][x] = 1;
					} else if (action == Action.TURN_OFF) {
						lights[y][x] = 0;
					} else if (action == Action.TOGGLE) {
						lights[y][x] ^= 1;
					}
				}
			}
			return lights;
		}

		private static Pattern directionPattern = Pattern.compile("(turn (?:on|off)|toggle) (\\d+),(\\d+) through (\\d+),(\\d+)");

		public static Direction parse(String line) {
			Matcher matcher = directionPattern.matcher(line);
			if (!matcher.matches()) {
				throw new RuntimeException(String.format("invalid input: %s", line));
			}
			String actionString = matcher.group(1);
			Action action = actionString.equals("turn on") ? Action.TURN_ON :
					actionString.equals("turn off") ? Action.TURN_OFF :
							actionString.equals("toggle") ? Action.TOGGLE : null;
			return new Direction(action, Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)),
					Integer.parseInt(matcher.group(5)));
		}

	}

	public static int countLights(int[][] lights) {
		int lit = 0;
		for (int y = 0; y < 1000; y++) {
			for (int x = 0; x < 1000; x++) {
				lit += lights[y][x];
			}
		}
		return lit;
	}

	public static class Puzzle1 {

		private static int[][] processLights(int[][] lights, Direction direction) {
			for (int y = direction.leftY; y <= direction.rightY; y++) {
				for (int x = direction.leftX; x <= direction.rightX; x++) {
					if (direction.action == Action.TURN_ON) {
						lights[y][x] = 1;
					} else if (direction.action == Action.TURN_OFF) {
						lights[y][x] = 0;
					} else if (direction.action == Action.TOGGLE) {
						lights[y][x] ^= 1;
					}
				}
			}
			return lights;
		}

		public static void main(String[] args) {
			int[][] lights = new int[1000][1000];
			getInput().stream().map(Direction::parse).sequential().forEach(d -> processLights(lights, d));
			System.out.println(countLights(lights));
		}

	}

	public static class Puzzle2 {

		private static int[][] processLights(int[][] lights, Direction direction) {
			for (int y = direction.leftY; y <= direction.rightY; y++) {
				for (int x = direction.leftX; x <= direction.rightX; x++) {
					if (direction.action == Action.TURN_ON) {
						lights[y][x]++;
					} else if (direction.action == Action.TURN_OFF) {
						lights[y][x] = Math.max(lights[y][x] - 1, 0);
					} else if (direction.action == Action.TOGGLE) {
						lights[y][x] += 2;
					}
				}
			}
			return lights;
		}

		public static void main(String[] args) {
			int[][] lights = new int[1000][1000];
			getInput().stream().map(Direction::parse).sequential().forEach(d -> processLights(lights, d));
			System.out.println(countLights(lights));
		}

	}

}
