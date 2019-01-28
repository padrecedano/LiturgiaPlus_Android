package org.deiverbum.app.model;

public class Misa {
    //public MisaLecturas misaLecturas;
    private LiturgiaPalabra liturgiaPalabra;

    /*
        public MisaLecturas getMisaLecturas() {
            return misaLecturas;
        }

        public void setMisaLecturas(MisaLecturas misaLecturas) {
            this.misaLecturas = misaLecturas;
        }
    */
    public LiturgiaPalabra getLiturgiaPalabra() {
        return liturgiaPalabra;
    }

    /*
        public String getLiturgiaPalabra() {
            return "liturgiaPalabra";
        }
    */
    public void setLiturgiaPalabra(LiturgiaPalabra liturgiaPalabra) {
        this.liturgiaPalabra = liturgiaPalabra;
    }
}
