package cw.javaweb.jsp;

    public class Dept {
        private String deptno;
        private String dname;
        private String loc;

        public Dept() {
        }

        public Dept(String deptno, String dname, String loc) {
            this.deptno = deptno;
            this.dname = dname;
            this.loc = loc;
        }

        public String getDeptno() {
            return deptno;
        }

        public void setDeptno(String deptno) {
            this.deptno = deptno;
        }

        public String getDname() {
            return dname;
        }

        public void setDname(String dname) {
            this.dname = dname;
        }

        public String getLoc() {
            return loc;
        }

        public void setLoc(String loc) {
            this.loc = loc;
        }

        @Override
        public String toString() {
            return "Dept{" + "deptno='" + deptno + '\'' + ", dname='" + dname + '\'' + ", loc='" + loc + '\'' + '}';
        }
    }


