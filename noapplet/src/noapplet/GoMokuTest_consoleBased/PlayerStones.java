package noapplet.GoMokuTest_consoleBased;

public enum PlayerStones {
    BLACK {
        @Override
        public Stone toStone() {
            return Stone.BLACK;
        }
    },
    WHITE {
        @Override
        public Stone toStone() {
            return Stone.WHITE;
        }
    };

    public abstract Stone toStone();
}
