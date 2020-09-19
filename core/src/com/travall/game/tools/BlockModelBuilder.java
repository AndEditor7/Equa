package com.travall.game.tools;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class BlockModelBuilder {
    /** The model currently being build */
    private BlockModel model;
    /** The node currently being build */
    private Node node;
    /** The mesh builders created between begin and end */
    private Array<MeshBuilder> builders = new Array<MeshBuilder>();


    private MeshBuilder getBuilder (final VertexAttributes attributes) {
        for (final MeshBuilder mb : builders)
            if (mb.getAttributes().equals(attributes) && mb.lastIndex() < Short.MAX_VALUE / 2) return mb;
        final MeshBuilder result = new MeshBuilder();
        result.begin(attributes);
        builders.add(result);
        return result;
    }

    /** Begin building a new model */
    public void begin () {
        if (model != null) throw new GdxRuntimeException("Call end() first");
        node = null;
        model = new BlockModel();
        builders.clear();
    }

    /** End building the model.
     * @return The newly created model. Call the {@link BlockModel#dispose()} method when no longer used. */
    public BlockModel end () {
        if (model == null) throw new GdxRuntimeException("Call begin() first");
        final BlockModel result = model;
        endnode();
        model = null;

        for (final MeshBuilder mb : builders)
            mb.end();
        builders.clear();

        rebuildReferences(result);
        return result;
    }

    private void endnode () {
        if (node != null) {
            node = null;
        }
    }

    /** Adds the {@link Node} to the model and sets it active for building. Use any of the part(...) method to add a NodePart. */
    protected Node node (final Node node) {
        if (model == null) throw new GdxRuntimeException("Call begin() first");

        endnode();

        model.nodes.add(node);
        this.node = node;

        return node;
    }

    /** Add a node to the model. Use any of the part(...) method to add a NodePart.
     * @return The node being created. */
    public Node node () {
        final Node node = new Node();
        node(node);
        node.id = "node" + model.nodes.size;
        return node;
    }

    /** Add the {@link Disposable} object to the model, causing it to be disposed when the model is disposed. */
    public void manage (final Disposable disposable) {
        if (model == null) throw new GdxRuntimeException("Call begin() first");
        model.manageDisposable(disposable);
    }

    /** Adds the specified MeshPart to the current Node. The Mesh will be managed by the model and disposed when the model is
     * disposed. The resources the Material might contain are not managed, use {@link #manage(Disposable)} to add those to the
     * model. */
    public void part (final MeshPart meshpart, final Material material) {
        if (node == null) node();
        node.parts.add(new NodePart(meshpart, material));
    }

    /** Creates a new MeshPart within the current Node and returns a {@link MeshPartBuilder} which can be used to build the shape of
     * the part. If possible a previously used {@link MeshPartBuilder} will be reused, to reduce the number of mesh binds.
     * Therefore you can only build one part at a time. The resources the Material might contain are not managed, use
     * {@link #manage(Disposable)} to add those to the model.
     * @return The {@link MeshPartBuilder} you can use to build the MeshPart. */
    public MeshPartBuilder part (final String id, int primitiveType, final VertexAttributes attributes, final Material material) {
        final MeshBuilder builder = getBuilder(attributes);
        part(builder.part(id, primitiveType), material);
        return builder;
    }

    /** Creates a new MeshPart within the current Node and returns a {@link MeshPartBuilder} which can be used to build the shape of
     * the part. If possible a previously used {@link MeshPartBuilder} will be reused, to reduce the number of mesh binds.
     * Therefore you can only build one part at a time. The resources the Material might contain are not managed, use
     * {@link #manage(Disposable)} to add those to the model.
     * @param attributes bitwise mask of the {@link com.badlogic.gdx.graphics.VertexAttributes.Usage}, only Position, Color, Normal
     *           and TextureCoordinates is supported.
     * @return The {@link MeshPartBuilder} you can use to build the MeshPart. */
    public MeshPartBuilder part (final String id, int primitiveType, final long attributes, final Material material) {
        return part(id, primitiveType, MeshBuilder.createAttributes(attributes), material);
    }

    /** Convenience method to create a model with a single node containing a box shape. The resources the Material might contain are
     * not managed, use {@link BlockModel#manageDisposable(Disposable)} to add those to the model.
     * @param attributes bitwise mask of the {@link com.badlogic.gdx.graphics.VertexAttributes.Usage}, only Position, Color, Normal
     *           and TextureCoordinates is supported. */
    public BlockModel createBox (float width, float height, float depth, int primitiveType, final Material material,
                            final long attributes) {
        begin();
        part("box", primitiveType, attributes, material).box(width, height, depth);
        return end();
    }

    /** Convenience method to create a model with a single node containing a rectangle shape. The resources the Material might
     * contain are not managed, use {@link BlockModel#manageDisposable(Disposable)} to add those to the model.
     * @param attributes bitwise mask of the {@link com.badlogic.gdx.graphics.VertexAttributes.Usage}, only Position, Color, Normal
     *           and TextureCoordinates is supported. */
    public BlockModel createRect (float x00, float y00, float z00, float x10, float y10, float z10, float x11, float y11, float z11,
                             float x01, float y01, float z01, float normalX, float normalY, float normalZ, int primitiveType, final Material material,
                             final long attributes) {
        begin();
        part("rect", primitiveType, attributes, material).rect(x00, y00, z00, x10, y10, z10, x11, y11, z11, x01, y01, z01, normalX,
                normalY, normalZ);
        return end();
    }

    /** Convenience method to create a model with a single node containing a cylinder shape. The resources the Material might
     * contain are not managed, use {@link BlockModel#manageDisposable(Disposable)} to add those to the model.
     * @param attributes bitwise mask of the {@link com.badlogic.gdx.graphics.VertexAttributes.Usage}, only Position, Color, Normal
     *           and TextureCoordinates is supported. */
    public BlockModel createCylinder (float width, float height, float depth, int divisions, int primitiveType,
                                 final Material material, final long attributes) {
        return createCylinder(width, height, depth, divisions, primitiveType, material, attributes, 0, 360);
    }

    /** Convenience method to create a model with a single node containing a cylinder shape. The resources the Material might
     * contain are not managed, use {@link BlockModel#manageDisposable(Disposable)} to add those to the model.
     * @param attributes bitwise mask of the {@link com.badlogic.gdx.graphics.VertexAttributes.Usage}, only Position, Color, Normal
     *           and TextureCoordinates is supported. */
    public BlockModel createCylinder (float width, float height, float depth, int divisions, int primitiveType,
                                 final Material material, final long attributes, float angleFrom, float angleTo) {
        begin();
        part("cylinder", primitiveType, attributes, material).cylinder(width, height, depth, divisions, angleFrom, angleTo);
        return end();
    }

    /** Convenience method to create a model with a single node containing a cone shape. The resources the Material might contain
     * are not managed, use {@link BlockModel#manageDisposable(Disposable)} to add those to the model.
     * @param attributes bitwise mask of the {@link com.badlogic.gdx.graphics.VertexAttributes.Usage}, only Position, Color, Normal
     *           and TextureCoordinates is supported. */
    public BlockModel createCone (float width, float height, float depth, int divisions, int primitiveType, final Material material,
                             final long attributes) {
        return createCone(width, height, depth, divisions, primitiveType, material, attributes, 0, 360);
    }

    /** Convenience method to create a model with a single node containing a cone shape. The resources the Material might contain
     * are not managed, use {@link BlockModel#manageDisposable(Disposable)} to add those to the model.
     * @param attributes bitwise mask of the {@link com.badlogic.gdx.graphics.VertexAttributes.Usage}, only Position, Color, Normal
     *           and TextureCoordinates is supported. */
    public BlockModel createCone (float width, float height, float depth, int divisions, int primitiveType, final Material material,
                             final long attributes, float angleFrom, float angleTo) {
        begin();
        part("cone", primitiveType, attributes, material).cone(width, height, depth, divisions, angleFrom, angleTo);
        return end();
    }


    /** Convenience method to create a model with a single node containing a sphere shape. The resources the Material might contain
     * are not managed, use {@link BlockModel#manageDisposable(Disposable)} to add those to the model.
     * @param attributes bitwise mask of the {@link com.badlogic.gdx.graphics.VertexAttributes.Usage}, only Position, Color, Normal
     *           and TextureCoordinates is supported. */
    public BlockModel createSphere (float width, float height, float depth, int divisionsU, int divisionsV, int primitiveType,
                               final Material material, final long attributes) {
        return createSphere(width, height, depth, divisionsU, divisionsV, primitiveType, material, attributes, 0, 360, 0, 180);
    }

    /** Convenience method to create a model with a single node containing a sphere shape. The resources the Material might contain
     * are not managed, use {@link BlockModel#manageDisposable(Disposable)} to add those to the model.
     * @param attributes bitwise mask of the {@link com.badlogic.gdx.graphics.VertexAttributes.Usage}, only Position, Color, Normal
     *           and TextureCoordinates is supported. */
    public BlockModel createSphere (float width, float height, float depth, int divisionsU, int divisionsV, int primitiveType,
                               final Material material, final long attributes, float angleUFrom, float angleUTo, float angleVFrom, float angleVTo) {
        begin();
        part("cylinder", primitiveType, attributes, material).sphere(width, height, depth, divisionsU, divisionsV, angleUFrom,
                angleUTo, angleVFrom, angleVTo);
        return end();
    }

    /** Convenience method to create a model with a single node containing a capsule shape. The resources the Material might contain
     * are not managed, use {@link BlockModel#manageDisposable(Disposable)} to add those to the model.
     * @param attributes bitwise mask of the {@link com.badlogic.gdx.graphics.VertexAttributes.Usage}, only Position, Color, Normal
     *           and TextureCoordinates is supported. */
    public BlockModel createCapsule (float radius, float height, int divisions, int primitiveType, final Material material,
                                final long attributes) {
        begin();
        part("capsule", primitiveType, attributes, material).capsule(radius, height, divisions);
        return end();
    }

    /** Resets the references to {@link Material}s, {@link Mesh}es and {@link MeshPart}s within the model to the ones used within
     * it's nodes. This will make the model responsible for disposing all referenced meshes. */
    public static void rebuildReferences (final BlockModel model) {
        model.materials.clear();
        model.meshes.clear();
        model.meshParts.clear();
        for (final Node node : model.nodes)
            rebuildReferences(model, node);
    }

    private static void rebuildReferences (final BlockModel model, final Node node) {
        for (final NodePart mpm : node.parts) {
            if (!model.materials.contains(mpm.material, true)) model.materials.add(mpm.material);
            if (!model.meshParts.contains(mpm.meshPart, true)) {
                model.meshParts.add(mpm.meshPart);
                if (!model.meshes.contains(mpm.meshPart.mesh, true)) model.meshes.add(mpm.meshPart.mesh);
                model.manageDisposable(mpm.meshPart.mesh);
            }
        }
        for (final Node child : node.getChildren())
            rebuildReferences(model, child);
    }

}