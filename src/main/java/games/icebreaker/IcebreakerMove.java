package games.icebreaker;

import iialib.games.model.IMove;

public class IcebreakerMove implements IMove {
    private IcebreakerPosition start;
    private IcebreakerPosition end;

    public IcebreakerMove(IcebreakerPosition start, IcebreakerPosition end) {
        this.start = start;
        this.end = end;
    }

    public IcebreakerMove(String string) {
        String[] strings = string.split("-");

        this.start = IcebreakerPositions.get(strings[0]);
        this.end = IcebreakerPositions.get(strings[1]);
    }

    public IcebreakerPosition getStart() {
        return start;
    }

    public IcebreakerPosition getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return this.start + "-" + this.end;
    }
}
