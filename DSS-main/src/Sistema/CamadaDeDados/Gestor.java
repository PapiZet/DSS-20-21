package Sistema.CamadaDeDados;

public class Gestor implements IdentificavelInteger {

        private Integer idGestor;
        private String password;
        private String nome;

        public Gestor(){
                this.idGestor = null;
                this.password = null;
                this.nome = null;
        }

        public Gestor(String nome, String password) {
                this.idGestor = null;
                this.password = password;
                this.nome = nome;
        }

        public Gestor(Integer idGestor, String nome, String password){
                this.idGestor = idGestor;
                this.password = password;
                this.nome = nome;
        }

        public Gestor(Gestor g){
                this(g.getId(), g.getNome(), g.getPassword());
        }

        public String getPassword(){
                return this.password;
        }

        public String getNome(){
                return this.nome;
        }

        public void setPassword(String password){
                this.password = password;
        }

        public void setNome(String nome){
                this.nome = nome;
        }

        public boolean checkPassword(String password){
                return this.password.equals(password);
        }

        public Gestor clone(){
                return new Gestor(this);
        }

        @Override
        public void setId(Integer id) {
                this.idGestor = id;
        }

        @Override
        public Integer getId() {
                return this.idGestor;
        }

}