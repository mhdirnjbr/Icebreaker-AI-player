package games.icebreaker;

public class IcebreakerNode {
    private IcebreakerPosition position;
    private IcebreakerNode parent;
    private int distance;

    public IcebreakerNode(IcebreakerPosition position, IcebreakerNode parent, int distance) {
        this.position = position;
        this.parent = parent;
        this.distance = distance;
    }

    public IcebreakerPosition getPosition() {
        return position;
    }

    public IcebreakerNode getParent() {
        return parent;
    }

    public int getDistance() {
        return distance;
    }

    public boolean equals(Object object) {
        if (object == this)
            return true;

        if (!(object instanceof IcebreakerNode))
            return false;

        IcebreakerNode node = (IcebreakerNode) object;

        return this.position.equals(node.position);
    }
}
