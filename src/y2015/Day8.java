package y2015;

import java.util.Arrays;
import java.util.List;

/**
 * TODO
 *
 * @author <a href="mailto:bombe@pterodactylus.net">David ‘Bombe’ Roden</a>
 */
public class Day8 {

    private static final String INPUT = "\"qxfcsmh\"\n" +
            "\"ffsfyxbyuhqkpwatkjgudo\"\n" +
            "\"byc\\x9dyxuafof\\\\\\xa6uf\\\\axfozomj\\\\olh\\x6a\"\n" +
            "\"jtqvz\"\n" +
            "\"uzezxa\\\"jgbmojtwyfbfguz\"\n" +
            "\"vqsremfk\\x8fxiknektafj\"\n" +
            "\"wzntebpxnnt\\\"vqndz\\\"i\\x47vvjqo\\\"\"\n" +
            "\"higvez\\\"k\\\"riewqk\"\n" +
            "\"dlkrbhbrlfrp\\\\damiauyucwhty\"\n" +
            "\"d\\\"\"\n" +
            "\"qlz\"\n" +
            "\"ku\"\n" +
            "\"yy\\\"\\\"uoao\\\"uripabop\"\n" +
            "\"saduyrntuswlnlkuppdro\\\\sicxosted\"\n" +
            "\"tj\"\n" +
            "\"zzphopswlwdhebwkxeurvizdv\"\n" +
            "\"xfoheirjoakrpofles\\\"nfu\"\n" +
            "\"q\\xb7oh\\\"p\\xce\\\"n\"\n" +
            "\"qeendp\\\"ercwgywdjeylxcv\"\n" +
            "\"dcmem\"\n" +
            "\"\\\"i\\x13r\\\"l\"\n" +
            "\"ikso\\xdcbvqnbrjduh\\\"uqudzki\\xderwk\"\n" +
            "\"wfdsn\"\n" +
            "\"pwynglklryhtsqbno\"\n" +
            "\"hcoj\\x63iccz\\\"v\\\"ttr\"\n" +
            "\"zf\\x23\\\\hlj\\\\kkce\\\\d\\\\asy\\\"yyfestwcdxyfj\"\n" +
            "\"xs\"\n" +
            "\"m\\\"tvltapxdvtrxiy\"\n" +
            "\"bmud\"\n" +
            "\"k\\\"a\"\n" +
            "\"b\\\"oas\"\n" +
            "\"\\\"yexnjjupoqsxyqnquy\\\"uzfdvetqrc\"\n" +
            "\"vdw\\xe3olxfgujaj\"\n" +
            "\"qomcxdnd\\\"\\\\cfoe\\\"\"\n" +
            "\"fpul\"\n" +
            "\"m\\\"avamefphkpv\"\n" +
            "\"vvdnb\\\\x\\\\uhnxfw\\\"dpubfkxfmeuhnxisd\"\n" +
            "\"hey\\\\\"\n" +
            "\"ldaeigghlfey\"\n" +
            "\"eure\\\"hoy\\xa5iezjp\\\\tm\"\n" +
            "\"yygb\\\"twbj\\\\r\\\"\\x10gmxuhmp\\\"\"\n" +
            "\"weirebp\\x39mqonbtmfmd\"\n" +
            "\"ltuz\\\\hs\\\"e\"\n" +
            "\"ysvmpc\"\n" +
            "\"g\\x8amjtt\\\"megl\\\"omsaihifwa\"\n" +
            "\"yimmm\"\n" +
            "\"iiyqfalh\"\n" +
            "\"cwknlaaf\"\n" +
            "\"q\\x37feg\\xc6s\\\"xx\"\n" +
            "\"uayrgeurgyp\\\\oi\"\n" +
            "\"xhug\\\"pt\\\"axugllbdiggzhvy\"\n" +
            "\"kdaarqmsjfx\\xc3d\"\n" +
            "\"\\\"vkwla\"\n" +
            "\"d\\\"\"\n" +
            "\"tmroz\\\"bvfinxoe\\\\mum\\\"wmm\"\n" +
            "\"\\\"n\\\"bbswxne\\\\p\\\\yr\\\"qhwpdd\"\n" +
            "\"skzlkietklkqovjhvj\\xfe\"\n" +
            "\"pbg\\\\pab\\\"bubqaf\\\"obzcwxwywbs\\\\dhtq\"\n" +
            "\"xxjidvqh\\\"lx\\\\wu\\\"ij\"\n" +
            "\"daef\\x5fe\\x5b\\\\kbeeb\\x13qnydtboof\"\n" +
            "\"ogvazaqy\\\"j\\x73\"\n" +
            "\"y\"\n" +
            "\"n\\\"tibetedldy\\\\gsamm\\\"nwu\"\n" +
            "\"wldkvgdtqulwkad\"\n" +
            "\"dpmxnj\"\n" +
            "\"twybw\\\"cdvf\\\"mjdajurokbce\"\n" +
            "\"ru\\\"\\\\lasij\\\"i\"\n" +
            "\"roc\\\\vra\\\\lhrm\"\n" +
            "\"pbkt\\x60booz\\\"fjlkc\"\n" +
            "\"j\\x4dytvjwrzt\"\n" +
            "\"\\\\uiwjkniumxcs\"\n" +
            "\"cbhm\\\"nexccior\\\"v\\\"j\\\"nazxilmfp\\x47\"\n" +
            "\"qdxngevzrlgoq\"\n" +
            "\"\\\"lrzxftytpobsdfyrtdqpjbpuwmm\\x9e\"\n" +
            "\"mdag\\x0asnck\\xc2ggj\\\"slb\\\"fjy\"\n" +
            "\"wyqkhjuazdtcgkcxvjkpnjdae\"\n" +
            "\"aixfk\\xc0iom\\x21vueob\"\n" +
            "\"dkiiakyjpkffqlluhaetires\"\n" +
            "\"ysspv\\\"lysgkvnmwbbsy\"\n" +
            "\"gy\\\"ryexcjjxdm\\\"xswssgtr\"\n" +
            "\"s\"\n" +
            "\"ddxv\"\n" +
            "\"qwt\\\"\\x27puilb\\\"pslmbrsxhrz\"\n" +
            "\"qdg\\xc9e\\\\qwtknlvkol\\x54oqvmchn\\\\\"\n" +
            "\"lvo\"\n" +
            "\"b\"\n" +
            "\"fk\\\"aa\\\"\\\"yenwch\\\\\\\\on\"\n" +
            "\"srig\\x63hpwaavs\\\\\\x80qzk\\\"xa\\\"\\xe6u\\\\wr\"\n" +
            "\"yxjxuj\\\"ghyhhxfj\\\"\\xa6qvatre\"\n" +
            "\"yoktqxjxkzrklkoeroil\"\n" +
            "\"\\\"jfmik\\\"\"\n" +
            "\"smgseztzdwldikbqrh\\\"\"\n" +
            "\"jftahgctf\\\"hoqy\"\n" +
            "\"tcnhicr\\\"znpgckt\\\"ble\"\n" +
            "\"vqktnkodh\\\"lo\\\"a\\\\bkmdjqqnsqr\"\n" +
            "\"ztnirfzqq\"\n" +
            "\"s\"\n" +
            "\"xx\"\n" +
            "\"iqj\\\"y\\\\hqgzflwrdsusasekyrxbp\\\\ad\"\n" +
            "\"\\\\xzjhlaiynkioz\\\"\\\"bxepzimvgwt\"\n" +
            "\"s\\x36rbw\"\n" +
            "\"mniieztwrisvdx\"\n" +
            "\"atyfxioy\\x2b\\\\\"\n" +
            "\"irde\\x85\\x5cvbah\\\\jekw\\\"ia\"\n" +
            "\"bdmftlhkwrprmpat\\\"prfaocvp\"\n" +
            "\"w\\\\k\"\n" +
            "\"umbpausy\"\n" +
            "\"zfauhpsangy\"\n" +
            "\"p\\\"zqyw\"\n" +
            "\"wtztypyqvnnxzvlvipnq\\\"zu\"\n" +
            "\"deicgwq\\\\oqvajpbov\\\\or\\\"kgplwu\"\n" +
            "\"mbzlfgpi\\\\\\\\zqcidjpzqdzxityxa\"\n" +
            "\"lfkxvhma\"\n" +
            "\"\\xf2yduqzqr\\\"\\\\fak\\\"p\\\"n\"\n" +
            "\"mpajacfuxotonpadvng\"\n" +
            "\"anb\\\\telzvcdu\\\\a\\xf2flfq\"\n" +
            "\"lrs\\\"ebethwpmuuc\\\"\\x86ygr\"\n" +
            "\"qmvdbhtumzc\\\"ci\"\n" +
            "\"meet\"\n" +
            "\"yopg\\x0fdxdq\\\"h\\\\ugsu\\xffmolxjv\"\n" +
            "\"uhy\"\n" +
            "\"fzgidrtzycsireghazscvmwcfmw\\\\t\"\n" +
            "\"cqohkhpgvpru\"\n" +
            "\"bihyigtnvmevx\\\"xx\"\n" +
            "\"xz\"\n" +
            "\"zofomwotzuxsjk\\\"q\\\"mc\\\"js\\\"dnmalhxd\"\n" +
            "\"\\\\ktnddux\\\\fqvt\\\"ibnjntjcbn\"\n" +
            "\"ia\"\n" +
            "\"htjadnefwetyp\\xd5kbrwfycbyy\"\n" +
            "\"\\\"\\\\hkuxqddnao\"\n" +
            "\"meqqsz\\x83luecpgaem\"\n" +
            "\"cvks\\x87frvxo\\\"svqivqsdpgwhukmju\"\n" +
            "\"sgmxiai\\\\o\\\"riufxwjfigr\\xdf\"\n" +
            "\"fgywdfecqufccpcdn\"\n" +
            "\"faghjoq\\x28abxnpxj\"\n" +
            "\"zuppgzcfb\\\"dctvp\\\"elup\\\"zxkopx\"\n" +
            "\"xqs\\x45xxdqcihbwghmzoa\"\n" +
            "\"anbnlp\\\\cgcvm\\\"hc\"\n" +
            "\"xf\\\"fgrngwzys\"\n" +
            "\"nrxsjduedcy\\x24\"\n" +
            "\"\\x71sxl\\\"gj\\\"sds\\\"ulcruguz\\\\t\\\\ssvjcwhi\"\n" +
            "\"jhj\\\"msch\"\n" +
            "\"qpovolktfwyiuyicbfeeju\\x01\"\n" +
            "\"nkyxmb\\\"qyqultgt\\\"nmvzvvnxnb\"\n" +
            "\"ycsrkbstgzqb\\\"uv\\\\cisn\"\n" +
            "\"s\"\n" +
            "\"ueptjnn\\\"\\\"sh\"\n" +
            "\"lp\\\"z\\\"d\\\"mxtxiy\"\n" +
            "\"yzjtvockdnvbubqabjourf\\\"k\\\"uoxwle\"\n" +
            "\"\\x82\\\"wqm\\\"\"\n" +
            "\"\\xb5cwtuks\\x5fpgh\"\n" +
            "\"wd\"\n" +
            "\"tbvf\"\n" +
            "\"ttbmzdgn\"\n" +
            "\"vfpiyfdejyrlbgcdtwzbnm\"\n" +
            "\"uc\"\n" +
            "\"otdcmhpjagqix\"\n" +
            "\"\\\\\\xb1qso\\\"s\"\n" +
            "\"scowax\"\n" +
            "\"behpstjdh\\xccqlgnqjyz\\\"eesn\"\n" +
            "\"r\\xe1cbnjwzveoomkzlo\\\\kxlfouhm\"\n" +
            "\"jgrl\"\n" +
            "\"kzqs\\\\r\"\n" +
            "\"ctscb\\x7fthwkdyko\\\"\\x62pkf\\\"d\\xe6knmhurg\"\n" +
            "\"tc\\\"kw\\x3ftt\"\n" +
            "\"bxb\\x5ccl\"\n" +
            "\"jyrmfbphsldwpq\"\n" +
            "\"jylpvysl\\\"\\\"juducjg\"\n" +
            "\"en\\\\m\\\"kxpq\\\"wpb\\\\\\\"\"\n" +
            "\"madouht\\\"bmdwvnyqvpnawiphgac\\\"\"\n" +
            "\"vuxpk\\\"ltucrw\"\n" +
            "\"aae\\x60arr\"\n" +
            "\"ttitnne\\\"kilkrgssnr\\xfdurzh\"\n" +
            "\"oalw\"\n" +
            "\"pc\\\"\\\"gktkdykzbdpkwigucqni\\\"nxiqx\"\n" +
            "\"dbrsaj\"\n" +
            "\"bgzsowyxcbrvhtvekhsh\\\"qgd\"\n" +
            "\"kudfemvk\\\"\\\"\\\"hkbrbil\\\"chkqoa\"\n" +
            "\"zjzgj\\\\ekbhyfzufy\"\n" +
            "\"\\\\acos\\\"fqekuxqzxbmkbnn\\x1ejzwrm\"\n" +
            "\"elxahvudn\\\"txtmomotgw\"\n" +
            "\"\\x2eoxmwdhelpr\\\"cgi\\xf7pzvb\"\n" +
            "\"eapheklx\"\n" +
            "\"hfvma\\\"mietvc\\\"tszbbm\\\"czex\"\n" +
            "\"h\\\"iiockj\\\\\\xc1et\"\n" +
            "\"d\\\"rmjjftm\"\n" +
            "\"qlvhdcbqtyrhlc\\\\\"\n" +
            "\"yy\\\"rsucjtulm\\\"coryri\\\"eqjlbmk\"\n" +
            "\"tv\"\n" +
            "\"r\\\"bfuht\\\\jjgujp\\\"\"\n" +
            "\"kukxvuauamtdosngdjlkauylttaokaj\"\n" +
            "\"srgost\\\"\\\"rbkcqtlccu\\x65ohjptstrjkzy\"\n" +
            "\"yxwxl\\\\yjilwwxffrjjuazmzjs\"\n" +
            "\"dxlw\\\\fkstu\\\"hjrtiafhyuoh\\\"sewabne\"\n" +
            "\"\\x88sj\\\"v\"\n" +
            "\"rfzprz\\xec\\\"oxqclu\\\"krzefp\\\\q\"\n" +
            "\"cfmhdbjuhrcymgxpylllyvpni\"\n" +
            "\"ucrmjvmimmcq\\x88\\xd9\\\"lz\"\n" +
            "\"lujtt\\\"\"\n" +
            "\"gvbqoixn\\\"pmledpjmo\\\"flydnwkfxllf\"\n" +
            "\"dvxqlbshhmelsk\\x8big\\\"l\"\n" +
            "\"mx\\x54lma\\x8bbguxejg\"\n" +
            "\"\\x66jdati\\xeceieo\"\n" +
            "\"\\\"iyyupixei\\x54ff\"\n" +
            "\"xohzf\\\"rbxsoksxamiu\"\n" +
            "\"vlhthspeshzbppa\\x4drhqnohjop\\\"\\\"mfjd\"\n" +
            "\"f\\\"tvxxla\\\"vurian\\\"\\\"idjq\\x3aptm\\xc3olep\"\n" +
            "\"gzqz\"\n" +
            "\"kbq\\\\wogye\\\\altvi\\\\hbvmodny\"\n" +
            "\"j\\xd8\"\n" +
            "\"ofjozdhkblvndl\"\n" +
            "\"hbitoupimbawimxlxqze\"\n" +
            "\"ypeleimnme\"\n" +
            "\"xfwdrzsc\\\\oxqamawyizvi\\\\y\"\n" +
            "\"enoikppx\\xa1ixe\\\"yo\\\"gumye\"\n" +
            "\"fb\"\n" +
            "\"vzf\"\n" +
            "\"zxidr\"\n" +
            "\"cu\\x31beirsywtskq\"\n" +
            "\"lxpjbvqzztafwezd\"\n" +
            "\"\\\\jyxeuo\\x18bv\"\n" +
            "\"b\\\"vawc\\\"p\\\\\\\\giern\\\"b\"\n" +
            "\"odizunx\\\"\\\"t\\\\yicdn\\\"x\\\"sdiz\"\n" +
            "\"\\\"\\\"tebrtsi\"\n" +
            "\"ctyzsxv\\xa6pegfkwsi\\\"tgyltaakytccb\"\n" +
            "\"htxwbofchvmzbppycccliyik\\xe5a\"\n" +
            "\"ggsslefamsklezqkrd\"\n" +
            "\"rcep\\\"fnimwvvdx\\\"l\"\n" +
            "\"zyrzlqmd\\x12egvqs\\\\llqyie\"\n" +
            "\"\\x07gsqyrr\\\\rcyhyspsvn\"\n" +
            "\"butg\\\"\"\n" +
            "\"gb\"\n" +
            "\"gywkoxf\\\"jsg\\\\wtopxvumirqxlwz\"\n" +
            "\"rj\\\"ir\\\"wldwveair\\x2es\\\"dhjrdehbqnzl\"\n" +
            "\"ru\\\"elktnsbxufk\\\\ejufjfjlevt\\\\lrzd\"\n" +
            "\"\\\"widsvok\"\n" +
            "\"oy\\\"\\x81nuesvw\"\n" +
            "\"ay\"\n" +
            "\"syticfac\\x1cfjsivwlmy\\\"pumsqlqqzx\"\n" +
            "\"m\"\n" +
            "\"rjjkfh\\x78cf\\x2brgceg\\\"jmdyas\\\"\\\\xlv\\xb6p\"\n" +
            "\"tmuvo\\\"\\x3ffdqdovjmdmkgpstotojkv\\\"as\"\n" +
            "\"jd\\\\ojvynhxllfzzxvbn\\\"wrpphcvx\"\n" +
            "\"pz\"\n" +
            "\"\\\"twr\"\n" +
            "\"n\\\\hdzmxe\\\"mzjjeadlz\"\n" +
            "\"fb\\\"rprxuagvahjnri\"\n" +
            "\"rfmexmjjgh\\\\xrnmyvnatrvfruflaqjnd\"\n" +
            "\"obbbde\\\"co\\\"qr\\\"qpiwjgqahqm\\\\jjp\\\"\"\n" +
            "\"vpbq\\\"\\\"y\\\"czk\\\\b\\x52ed\\\"lnzepobp\"\n" +
            "\"syzeajzfarplydipny\\\"y\\\"\\xe8ad\"\n" +
            "\"mpyodwb\"\n" +
            "\"\\x47rakphlqqptd\"\n" +
            "\"wa\\\"oj\\\"aiy\"\n" +
            "\"a\"\n" +
            "\"ropozx\"\n" +
            "\"q\\x51nbtlwa\"\n" +
            "\"etukvgx\\\\jqxlkq\"\n" +
            "\"\\\"tp\\\"rah\\\"pg\\\"s\\\"bpdtes\\\\tkasdhqd\"\n" +
            "\"dn\\\"qqpkikadowssb\\xcah\\\"dzpsf\\\\ect\\\"jdh\"\n" +
            "\"pxunovbbrrn\\\\vullyn\\\"bno\\\"\\\"\\\"myfxlp\\\"\"\n" +
            "\"qaixyazuryvkmoulhcqaotegfj\\\\mpzm\"\n" +
            "\"bvfrbicutzbjwn\\\\oml\\\"cf\\\"d\\\"ezcpv\\\"j\"\n" +
            "\"rmbrdtneudemigdhelmb\"\n" +
            "\"aq\\\\aurmbhy\"\n" +
            "\"wujqvzw\"\n" +
            "\"gf\\\"tssmvm\\\"gm\\\"hu\\x9a\\xb7yjawsa\"\n" +
            "\"hrhqqxow\\xe2gsydtdspcfqy\\\"zw\\\\ou\"\n" +
            "\"ianwwf\\\\yko\\\\tdujhhqdi\"\n" +
            "\"xylz\\\"zpvpab\"\n" +
            "\"lwuopbeeegp\"\n" +
            "\"aoop\\x49jhhcexdmdtun\"\n" +
            "\"\\\\\\\\mouqqcsgmz\"\n" +
            "\"tltuvwhveau\\x43b\\\"ymxjlcgiymcynwt\"\n" +
            "\"gsugerumpyuhtjljbhrdyoj\"\n" +
            "\"lnjm\\xb8wg\\\"ajh\"\n" +
            "\"zmspue\\\"nfttdon\\\\b\\\"eww\"\n" +
            "\"\\\"w\\x67jwaq\\x7ernmyvs\\\\rmdsuwydsd\\\"th\"\n" +
            "\"ogtgvtlmcvgllyv\"\n" +
            "\"z\\\"fqi\\\"rvddoehrciyl\"\n" +
            "\"yustxxtot\\\"muec\\\"xvfdbzunzvveq\"\n" +
            "\"mqslw\"\n" +
            "\"txqnyvzmibqgjs\\xb6xy\\x86nfalfyx\"\n" +
            "\"kzhehlmkholov\"\n" +
            "\"plpmywcnirrjutjguosh\\\\\"\n" +
            "\"pydbnqofv\\\"dn\\\\m\"\n" +
            "\"aegqof\"\n" +
            "\"eambmxt\\\\dxagoogl\\\\zapfwwlmk\"\n" +
            "\"afbmqitxxqhddlozuxcpjxgh\"\n" +
            "\"vgts\"\n" +
            "\"bfdpqtoxzzhmzcilehnflna\"\n" +
            "\"s\\\"idpz\"\n" +
            "\"\\xcfhgly\\\"nlmztwybx\\\"ecezmsxaqw\"\n" +
            "\"aackfgndqcqiy\"\n" +
            "\"\\x22unqdlsrvgzfaohoffgxzfpir\\\"s\"\n" +
            "\"abh\\\"ydv\\\"kbpdhrerl\"\n" +
            "\"bdzpg\"\n" +
            "\"ekwgkywtmzp\"\n" +
            "\"wtoodejqmrrgslhvnk\\\"pi\\\"ldnogpth\"\n" +
            "\"njro\\x68qgbx\\xe4af\\\"\\\\suan\"";

    private static List<String> getInput() {
        return Arrays.asList(INPUT.split("\n"));
    }

    public static int charactersOfCode(String line) {
        return line.length();
    }

    public static int charactersInMemory(String line) {
        int inMemory = -2;
        boolean lastWasSlash = false;
        for (char c : line.toCharArray()) {
            if (lastWasSlash) {
                if (c == 'x') {
                    inMemory -= 2;
                }
                lastWasSlash = false;
                inMemory--;
            } else {
                if (c == '\\') {
                    lastWasSlash = true;
                }
            }
            inMemory++;
        }
        return inMemory;
    }

    public static int encodedCharacterCount(String line) {
        int characterCount = 2;
        for (char c : line.toCharArray()) {
            if (c == '\\') {
                characterCount++;
            } else if (c == '"') {
                characterCount++;
            }
            characterCount++;
        }
        return characterCount;
    }

    public static class Puzzle1 {

        public static void main(String... arguments) {
            int charactersOfCode = getInput().stream().map(Day8::charactersOfCode).mapToInt(i -> i).sum();
            int inMemory = getInput().stream().map(Day8::charactersInMemory).mapToInt(i -> i).sum();
            System.out.println(charactersOfCode - inMemory);
        }

    }

    public static class Puzzle2 {

        public static void main(String... arguments) {
            int charactersOfCode = getInput().stream().map(Day8::charactersOfCode).mapToInt(i -> i).sum();
            int encodedCharacters = getInput().stream().map(Day8::encodedCharacterCount).mapToInt(i -> i).sum();
            System.out.println(encodedCharacters - charactersOfCode);
        }

    }

}
